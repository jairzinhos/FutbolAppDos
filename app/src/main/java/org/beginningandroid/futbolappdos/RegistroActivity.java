package org.beginningandroid.futbolappdos;

import android.app.ApplicationExitInfo;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    private EditText etEmail;
    //private FirebaseFirestore db;

    // creating a variable for
    // our Firebase Database.
    //private FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
     private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        etEmail = findViewById(R.id.etEmail);
        Button btnRegistrar = findViewById(R.id.btnRegistrar);

        btnRegistrar.setOnClickListener(v -> registrarUsuario());
    }

    private void registrarUsuario() {
        String email = etEmail.getText().toString();
        String hybridId = DeviceUtils.getHybridId(this);
        long approvedAt = System.currentTimeMillis();
        long expiresAt = approvedAt + (6L * 30 * 24 * 60 * 60 * 1000); // 6 meses en ms

        // Crear objeto usuario
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("isApproved", false);
        user.put("approvedAt", approvedAt);
        user.put("expiresAt", expiresAt);

        // Guardar en Realtime Database
        databaseReference.child(hybridId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Registro exitoso. Espere su aprobación", Toast.LENGTH_SHORT).show();
                    // Ahora elimino todas las actividades o salgo de la app
                    finishAndRemoveTask();
                    //finish();
                });

        /*
        String userId = null;
        boolean isApproved = false;
        Calendar approvedAt = Calendar.getInstance();
        Calendar expiresAt;
        expiresAt.add(Calendar.MONTH,6);
        Calendar expiresAt

        approvedAt.add(Calendar.MONTH,6);
        expiresAt

        // of our Firebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get
        // reference for our database.

        databaseReference = firebaseDatabase.getReference("users");

        databaseReference.child("users").child(userId).setValue(email);
        databaseReference.child("users").child(userId).setValue(hybridId);
        databaseReference.child("users").child(userId).setValue(isApproved);
        databaseReference.child("users").child(userId).setValue(approvedAt);
        databaseReference.child("users").child(userId).setValue(expiresAt);


            .addOnSuccessListener(aVoid -> {
            Toast.makeText(this, "Registro exitoso, avise al Dev para su aprobación", Toast.LENGTH_SHORT).show();
            finish();
        });


        // Crear documento en Firestore
        Map<String, Object> user = new HashMap<>();
        user.put("hybridId", hybridId);
        user.put("email", email);
        user.put("isApproved", false);

        firebaseDatabase.collection("users").document(hybridId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    finish();
                });

         */
    }
}
