package com.encrypt.encriprsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {

    Button Registrarse;

    TextView Email;
    TextView Password;

    FirebaseAuth firebaseAuth;

    boolean Minus = false;
    boolean Mayus = false;
    boolean Special = false;
    boolean Tam = false;
    boolean Number = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();


        Registrarse = findViewById(R.id.btnRegistrarse);
        Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Email = findViewById(R.id.txtEmail);
                String Correo = Email.getText().toString();
                Password = findViewById(R.id.txtPassword);
                String Contraseña = Password.getText().toString();

                /**
                 * Verficamos la contraseña y el email
                 */
                if(!Correo.isEmpty() && !Contraseña.isEmpty()){

                    /**
                     * Verificamos la seguridad de la contraseña que se esta creando
                     */
                    if (Contraseña.length() >= 8) {
                        Tam = true;
                    }
                    for (int i = 0; i < Contraseña.length(); i++) {
                        char pwd = Contraseña.charAt(i);
                        int ascii = (int)pwd;
                        if (ascii >= 65 && ascii <= 90) {
                            Mayus = true;
                        } else if (ascii >= 97 && ascii <= 122) {
                            Minus = true;
                        } else if (ascii >= 48 && ascii <= 57) {
                            Number = true;
                        } else if ((ascii >= 33 && ascii <= 47) || (ascii >= 58 && ascii <= 64) || (ascii >= 91 && ascii <= 96) || (ascii >= 123 && ascii <= 126)) {
                            Special = true;
                        }
                    }

                    /**
                     * Por cada condicion que no se cumpla se informa al usuario
                     */

                    if(!Tam){
                        Toast.makeText(Login.this, "La contraseña debe tener 8 o más caracteres", Toast.LENGTH_SHORT).show();
                    }
                    if(!Minus){
                        Toast.makeText(Login.this, "La contraseña debe tener al menos una letra Minúscula", Toast.LENGTH_SHORT).show();
                    }
                    if(!Mayus){
                        Toast.makeText(Login.this, "La contraseña debe tener al menos una letra Mayúscula", Toast.LENGTH_SHORT).show();
                    }
                    if(!Special){
                        Toast.makeText(Login.this, "La contraseña debe tener al menos un caracter especial", Toast.LENGTH_SHORT).show();
                    }
                    if(!Number) {
                        Toast.makeText(Login.this, "La contraseña debe tener al menos un número", Toast.LENGTH_SHORT).show();
                    }

                    if(Tam == true && Minus == true && Mayus == true && Special == true && Number == true) {
                        firebaseAuth.createUserWithEmailAndPassword(Correo, Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Login.this, "Fue registrado con éxito", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(Login.this, "Hubo un error con su registro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(Login.this, "La contraseña debe cumplir con todos los requisitos requeridos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Login.this, "Introduzca sus nuevos datos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}