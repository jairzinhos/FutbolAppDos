package org.beginningandroid.futbolappdos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private DatabaseReference usersRef;

    private List<String> allPendingUsers = new ArrayList<>(); // Lista completa
    private List<String> pendingUsers = new ArrayList<>();

    // Declara la variable del ListView
    ListView lvPendingUsers;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Inicializa el ListView con el ID del XML
        lvPendingUsers = findViewById(R.id.lvPendingUsers); // ¡Esta línea es clave!

        // Aquí iría la lógica del panel de administración
        // (ej: listar usuarios, aprobar/desaprobar, etc.)

        usersRef = FirebaseDatabase.getInstance().getReference("users");
        ListView listView = findViewById(R.id.lvPendingUsers);
        EditText etSearch = findViewById(R.id.etSearch);
        Button btnRefresh = findViewById(R.id.btnRefresh);

        // Configurar adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pendingUsers);
        //listView.setAdapter(adapter);
        lvPendingUsers.setAdapter(adapter);

        // Cargar usuarios pendientes
        loadPendingUsers();

        // Buscar usuarios
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterUsers(s.toString().toLowerCase(Locale.ROOT));
            }
            //... otros métodos del TextWatcher
        });

        // Actualizar lista
        btnRefresh.setOnClickListener(v -> loadPendingUsers());

        // Aprobar usuario
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedUser = pendingUsers.get(position);
            showApprovalDialog(extractHybridIdFromString(selectedUser));
        });

    }

    private void loadPendingUsers() {
        usersRef.orderByChild("isApproved").equalTo(false)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        allPendingUsers.clear();
                        pendingUsers.clear();
                        for(DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String hybridId = userSnapshot.getKey();
                            String email = userSnapshot.child("email").getValue(String.class);
                            ///pendingUsers.add(hybridId + "\n" + email);
                            String userEntry = hybridId + "\n" + email;

                            allPendingUsers.add(userEntry);
                            pendingUsers.add(userEntry);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("Admin", "Error loading users: " + error.getMessage());

                    }
                    //... onCancelled
                });
    }

    private void showApprovalDialog(String hybridId) {
        new AlertDialog.Builder(this)
                .setTitle("Aprobar usuario")
                .setMessage("¿Deseas aprobar este usuario por 6 meses?")
                .setPositiveButton("Aprobar", (dialog, which) -> approveUser(hybridId))
                .setNegativeButton("Cancelar", null)
                .show();
    }
    private void approveUser(String hybridId) {
        long approvedAt = System.currentTimeMillis();
        long expiresAt = approvedAt + 15778463000L; // 6 meses en milisegundos

        Map<String, Object> updates = new HashMap<>();
        updates.put("isApproved", true);
        updates.put("approvedAt", approvedAt);
        updates.put("expiresAt", expiresAt);

        usersRef.child(hybridId).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Usuario aprobado", Toast.LENGTH_SHORT).show();
                    loadPendingUsers(); // Actualizar lista
                });
    }
    private String extractHybridIdFromString(String userString) {
        return userString.split("\n")[0]; // Obtener la primera parte antes del salto de línea
    }

    private void filterUsers(String query) {
        List<String> filteredList = new ArrayList<>();

        for (String user : allPendingUsers) { // Lista completa de usuarios
            if (user.toLowerCase().contains(query)) {
                filteredList.add(user);
            }
        }

        // Actualizar el adaptador
        adapter.clear();
        adapter.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
        // 1. Obtener el usuario admin almacenado localmente
        SharedPreferences prefs = getSharedPreferences("AdminSession", MODE_PRIVATE);
        String adminUsername = prefs.getString("admin_username", null);

        if (adminUsername == null) {
            // No hay sesión activa, redirigir a login
            redirectToLogin();
            return;
        }

        // 2. Verificar en Firebase si el admin aún existe
        FirebaseDatabase.getInstance().getReference("admins")
                .child(adminUsername)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            // Admin fue eliminado de la base de datos
                            showSessionExpiredMessage();
                            redirectToLogin();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("AdminCheck", "Error de conexión: " + error.getMessage());
                    }
                });
    }
    private void showSessionExpiredMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Sesión expirada")
                .setMessage("Tu cuenta de admin ha sido removida")
                .setPositiveButton("Aceptar", (dialog, which) -> redirectToLogin())
                .show();
    }

    private void redirectToLogin() {
        // Limpiar SharedPreferences
        SharedPreferences prefs = getSharedPreferences("AdminSession", MODE_PRIVATE);
        prefs.edit().clear().apply();

        // Redirigir a Login
        startActivity(new Intent(this, LoginAdminActivity.class));
        finish();
    }
}
