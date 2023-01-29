package com.encrypt.encriprsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    Button btnStart;
    Button btnRegister;

    TextView Email;
    TextView Pass;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();

        btnStart = findViewById(R.id.btnComenzar);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Email = findViewById(R.id.SigInEmail);
                String Correo = Email.getText().toString();
                Pass = findViewById(R.id.SigIngPassword);
                String Contraseña = Pass.getText().toString();

                if(!Correo.isEmpty() && !Contraseña.isEmpty()){
                    firebaseAuth.signInWithEmailAndPassword(Correo,Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(MainActivity.this, LlavesRSA.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(MainActivity.this, "Introduzca los datos correspondientes", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnRegister = findViewById(R.id.btnIrRegistrar);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }




}