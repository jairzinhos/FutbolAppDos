package org.beginningandroid.futbolappdos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private DatabaseReference adminsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        adminsRef = FirebaseDatabase.getInstance().getReference("admins");

        findViewById(R.id.btnLogin).setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (!username.isEmpty() && !password.isEmpty()) {
                authenticateAdmin(username, password);
            } else {
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void authenticateAdmin(String username, String password) {

        // 1. Generar el hash de la contraseña ingresada
        String inputHash = sha256(password); // Generar el hash

        adminsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // 2. Obtener valores de la base de datos
                    String storedHash = snapshot.child("password").getValue(String.class);
                    String role = snapshot.child("role").getValue(String.class);

                    String inputHash = sha256(password);

                    // 3. Comparar hashes y verificar rol
                    if (inputHash.equals(storedHash) && "admin".equals(role)) {
                        onAdminLoginSuccess(username); // Llamar al método de éxito
                    } else {

                        showLoginError("Credenciales inválidas");
                        //Toast.makeText(LoginAdminActivity.this,
                                //"Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginAdminActivity.this,
                            "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("AdminAuth", "Error: " + error.getMessage());
            }
        });
        // Al autenticar, verificar el rol (opcional)
        adminsRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String storedHash = snapshot.child("password").getValue(String.class);
                    String role = snapshot.child("role").getValue(String.class); // <--- Nuevo

                    if (role != null && role.equals("admin") && inputHash.equals(storedHash)) {
                        onAdminLoginSuccess(username);
                    } else {
                        Toast.makeText(LoginAdminActivity.this,
                                "Acceso denegado", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
            //... onCancelled
        });
    }

    // Generar hash SHA-256 de la contraseña
    private String sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("SHA256", "Error: " + e.getMessage());
            return null;
        }
    }
    // Método para manejar login exitoso
    private void onAdminLoginSuccess(String username) {
        // Guardar en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("AdminSession", MODE_PRIVATE);
        prefs.edit().putString("admin_username", username).apply();

        // Redirigir a AdminActivity
        startActivity(new Intent(this, AdminActivity.class));
        finish();
    }
    private void showLoginError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
