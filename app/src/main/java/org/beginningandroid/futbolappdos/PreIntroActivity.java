package org.beginningandroid.futbolappdos;

//import static android.support.v4.media.MediaBrowserCompatApi21.isConnected;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class PreIntroActivity extends AppCompatActivity {

    Context context;

    TextView textDos;

    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preintro);
        textDos = findViewById(R.id.textViewDos);


        if (!isConnected()) {
            Toast.makeText(PreIntroActivity.this, " No Internet Access", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PreIntroActivity.this, " Welcome to our App", Toast.LENGTH_SHORT).show();

        }

        //Approval Verification Section (6 Months)
        String hybridId = DeviceUtils.getHybridId(this);
        userRef = FirebaseDatabase.getInstance().getReference("users").child(hybridId);

        // Escuchar cambios en la aprobación
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Boolean isApproved = snapshot.child("isApproved").getValue(Boolean.class);
                    Long expiresAt = snapshot.child("expiresAt").getValue(Long.class);

                    if (isApproved != null && isApproved && expiresAt != null) {
                        Date expirationDate = new Date(expiresAt);
                        Date today = new Date();
                        //Boolean decision = new BooleanDate().before(expirationDate);
                        if (new Date().before(expirationDate)) {
                            String Dia = "viernes";
                            Log.d("Datos de E y A", "expiración: " + expirationDate);
                            Log.d("Datos de E y A", "aprobación: " + today);
                            Log.d("Datos de E y A", "Respuesta: " + isApproved);
                            Log.d("Datos de E y A", "Dia " + Dia);


                            Toast.makeText(PreIntroActivity.this, " Usuario registrado", Toast.LENGTH_SHORT).show();
                            //goToMainActivity();
                            startActivity(new Intent(PreIntroActivity.this, IntroActivity.class));
                            finish();
                            return;
                        }
                    }
                }
                //Here create a blockedActivity instead of SplashActivity
                startActivity(new Intent(PreIntroActivity.this, RegistroActivity.class));
                Toast.makeText(PreIntroActivity.this, " Si ya se registró, espere validación o comuníquese con (te.me/Devjabu)", Toast.LENGTH_SHORT).show();
                Toast.makeText(PreIntroActivity.this, " O comuníquese con (te.me/Devjabu)", Toast.LENGTH_SHORT).show();
                String Dia = "jueves";
                Log.d("Datos de E y A", "Dia " + Dia);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RTDB", "Error: " + error.getMessage());
            }
        });


    }

    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(context.CONNECTIVITY_SERVICE);

        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}
