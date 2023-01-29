package com.encrypt.encriprsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.util.Random;

public class LlavesRSA extends AppCompatActivity {
    Button btnEncrypt;
    Button btnDesencrypt;
    Button btnGenerate;
    Button btnCopyPriv;
    Button btnCopyPub;
    Button btnIrLista;

    EditText KeyPub;
    EditText KeyPriv;

    TextView txtKeyPriv;
    TextView txtKeyPub;

    private BigInteger n, d, e;
    private int bitlen = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_llaves_rsa);


        KeyPub = findViewById(R.id.OutputllavePublica);
        KeyPriv = findViewById(R.id.OutputllavePrivada);

        btnCopyPub = findViewById(R.id.copyPublic);
        btnCopyPriv = findViewById(R.id.copyPriv);

        btnEncrypt = findViewById(R.id.btnIrEncriptar);
        btnDesencrypt = findViewById(R.id.btnIrDesencriptar);

        btnIrLista = findViewById(R.id.btnIrListaLlaves);

        btnGenerate = findViewById(R.id.btnGenerar);

        /**
         * Copiado de la llave Publica
         */
        btnCopyPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText",KeyPub.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(LlavesRSA.this, "Llave Pública copiada con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Copiado de la llave Privada
         */
        btnCopyPriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText",KeyPriv.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(LlavesRSA.this, "Llave Privada copiada con éxito", Toast.LENGTH_SHORT).show();
            }
        });


        /**
         * Ir a Encriptacion
         */
        btnEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IrEncrypt = new Intent(LlavesRSA.this, Encriptar.class);
                startActivity(IrEncrypt);
            }
        });
        /**
         * Ir a Desencriptacion
         */
        btnDesencrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent IrDesencrypt = new Intent(LlavesRSA.this, Desencriptar.class);
                startActivity(IrDesencrypt);
            }
        });
        /**
         * Ir a ListaLlaves
         */
        btnIrLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IrLista = new Intent(LlavesRSA.this, ListaLlaves.class);
                startActivity(IrLista);


            }
        });

        /**
         * Generar las llaves publica y privada
         */
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearLlaves();
                txtKeyPriv = findViewById(R.id.OutputllavePrivada);
                txtKeyPriv.setText(getKeyPrivate());
                txtKeyPub = findViewById(R.id.OutputllavePublica);
                txtKeyPub.setText(getKeyPublic());

                Toast.makeText(LlavesRSA.this, "Llaves Generadas con éxito", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Crea un par de claves pública y privada con una longitud de 1024 bits
     */
    public void CrearLlaves() {
        Random r = new Random();
        BigInteger p = new BigInteger(bitlen / 2, 100, r);
        BigInteger q = new BigInteger(bitlen / 2, 100, r);
        n = p.multiply(q);
        BigInteger m = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = new BigInteger("3");
        while (m.gcd(e).intValue() > 1) {
            e = e.add(new BigInteger("2"));
        }
        d = e.modInverse(m);
    }

    public String getKeyPrivate(){
        return n.toString() + "," + d.toString();
    }

    public String getKeyPublic(){
        return n.toString() + "," + e.toString();
    }
}