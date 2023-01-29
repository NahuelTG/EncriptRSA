package com.encrypt.encriprsa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;

public class Desencriptar extends AppCompatActivity {
    TextView Desencriptado;
    EditText KeyPriv;
    EditText Msj;

    private BigInteger n, d;
    Button btnDesencrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desencriptar);
        btnDesencrypt = findViewById(R.id.btnDesencriptar);
        btnDesencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KeyPriv = findViewById(R.id.InputLlavePrivada);
                Msj = findViewById(R.id.DesencriptarMsj);
                RSADesencryptor(KeyPriv.getText().toString(), Msj.getText().toString());
            }
        });
    }

    public void RSADesencryptor(String privateKey, String encryptedMessage) {
        // Split the string into two parts separated by a comma
        String[] parts = privateKey.split(",");
        // Parse the two parts into BigIntegers
        n = new BigInteger(parts[0]);
        d = new BigInteger(parts[1]);
        String decryptedMessage = decrypt(encryptedMessage);
        Desencriptado = findViewById(R.id.DesencryptResult);
        Desencriptado.setText(decryptedMessage);
        //System.out.println("Decrypted message: " + decryptedMessage);
    }

    /**
     * Desencripta el mensaje utilizando la clave privada
     *
     * @param message
     *            - el mensaje a desencriptar
     * @return el mensaje desencriptado
     */
    public synchronized String decrypt(String message) {
        return new String((new BigInteger(message)).modPow(d, n).toByteArray());
    }
}