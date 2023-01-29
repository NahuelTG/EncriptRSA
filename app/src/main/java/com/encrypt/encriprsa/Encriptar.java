package com.encrypt.encriprsa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;

public class Encriptar extends AppCompatActivity {
    TextView Encriptado;
    TextView KeyPub;
    TextView Msj;
    TextView MsjEncript;


    private BigInteger n, e;
    Button btnEncrypt;
    Button Copy;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encriptar);
        btnEncrypt = findViewById(R.id.btnEncriptar);
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyPub = findViewById(R.id.InputLlavePublica);
                Msj = findViewById(R.id.EncriptarMsj);
                String Key = KeyPub.getText().toString();
                //System.out.println("Aqui llave:" +Key+"");
                String mes = Msj.getText().toString();
                //System.out.println("Aqui mes:" +mes+"");

                RSAEncryptor(Key, mes);

            }
        });
                MsjEncript = findViewById(R.id.EncryptResult);
                Copy = findViewById(R.id.btnCopyMessage);
                Copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("EditText",MsjEncript.getText().toString());
                        clipboard.setPrimaryClip(clip);

                        Toast.makeText(Encriptar.this, "Copiado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void RSAEncryptor(String publicKey, String message) {
        //System.out.println("Aqui todo bien 1");
        String[] parts = publicKey.split(",");
        //System.out.println("Aqui todo bien 2");
        n = new BigInteger(parts[0]);
        e = new BigInteger(parts[1]);

        System.out.println("Aqui todo bien 3");
        String encryptedMessage = encrypt(message);
        Encriptado = findViewById(R.id.EncryptResult);
        Encriptado.setText(encryptedMessage);
        //System.out.println("Encrypted message: " + encryptedMessage);
    }

    /**
     * Encripta el mensaje utilizando la clave pública
     *
     * @param message
     *            - el mensaje a encriptar
     * @return el mensaje encriptado
     */
    public synchronized String encrypt(String message) {
        return (new BigInteger(message.getBytes())).modPow(e, n).toString();
    }


}