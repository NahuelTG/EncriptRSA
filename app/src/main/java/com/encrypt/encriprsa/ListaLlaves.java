package com.encrypt.encriprsa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import Model.Llave;

public class ListaLlaves extends AppCompatActivity {

    private List<Llave> llaveList = new ArrayList<>();
    ArrayAdapter<Llave> arrayAdapterLlave;

    Button Guardar;
    Button Actualizar;
    Button Borrar;
    Button CopyPriv;
    Button CopyPub;

    TextView Description;
    EditText KeyPub;
    TextView KeyPriv;

    ListView listKeys;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Llave keySelectd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_llaves);

        /**
         * Inicializar Firebase
         */
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();


        ListarDatos();

        /**
         * Seleccionamos algun item de la List View
         */
        listKeys = findViewById(R.id.lv_listaKeys);
        listKeys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                KeyPriv = findViewById(R.id.txtKeyPriv);
                Description = findViewById(R.id.txtDescription);
                KeyPub = findViewById(R.id.txtKeyPub);

                keySelectd = (Llave) parent.getItemAtPosition(position);
                Description.setText(keySelectd.getDescription());
                KeyPub.setText(keySelectd.getKeyPub());
                KeyPriv.setText(keySelectd.getKeyPriv());
            }
        });



        Guardar = findViewById(R.id.btnGuardar);
        Actualizar = findViewById(R.id.btnActualizar);
        Borrar = findViewById(R.id.btnBorrar);



        Guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyPriv = findViewById(R.id.txtKeyPriv);
                Description = findViewById(R.id.txtDescription);
                KeyPub = findViewById(R.id.txtKeyPub);
                String Descrip = Description.getText().toString();
                String KeyPb = KeyPub.getText().toString();
                String KeyPv = KeyPriv.getText().toString();

                if(Descrip.equals("") || KeyPb.equals("") || KeyPv.equals("")){
                    if(Descrip.equals("")) {
                        Description.setError("Required");
                    }else if(KeyPb.equals("")){
                        KeyPub.setError("Required");
                    }else if(KeyPv.equals("")){
                        KeyPriv.setError("Required");
                    }
                }else{
                    Llave Key = new Llave();
                    Key.setId((String.valueOf(UUID.randomUUID())));
                    databaseReference.child("Llave").child(Key.getId()).child("id").setValue(Key.getId());
                    databaseReference.child("Llave").child(Key.getId()).child("keyPub").setValue(KeyPub.getText().toString());
                    databaseReference.child("Llave").child(Key.getId()).child("keyPriv").setValue(KeyPriv.getText().toString());
                    databaseReference.child("Llave").child(Key.getId()).child("description").setValue(Description.getText().toString());
                    Toast.makeText(ListaLlaves.this, "Se guardo con éxito", Toast.LENGTH_SHORT).show();
                    limpiarcajas();
                }
            }
        });

        Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyPriv = findViewById(R.id.txtKeyPriv);
                Description = findViewById(R.id.txtDescription);
                KeyPub = findViewById(R.id.txtKeyPub);
                String Descrip = Description.getText().toString();
                String KeyPb = KeyPub.getText().toString();
                String KeyPv = KeyPriv.getText().toString();

                if(Descrip.equals("") || KeyPb.equals("") || KeyPv.equals("")){
                    if(Descrip.equals("")) {
                        Description.setError("Required");
                    }else if(KeyPb.equals("")){
                        KeyPub.setError("Required");
                    }else if(KeyPv.equals("")){
                        KeyPriv.setError("Required");
                    }
                }else{
                    Llave Key = new Llave();
                    Key.setId(keySelectd.getId());
                    Key.setDescription(Description.getText().toString().trim());
                    Key.setKeyPub(KeyPub.getText().toString().trim());
                    Key.setKeyPriv(KeyPriv.getText().toString().trim());
                    databaseReference.child("Llave").child(Key.getId()).setValue(Key);
                    Toast.makeText(ListaLlaves.this, "Se actualizo con éxito", Toast.LENGTH_SHORT).show();
                    limpiarcajas();

                }
            }
        });

        Borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Llave Key = new Llave();
                Key.setId(keySelectd.getId());
                databaseReference.child("Llave").child(Key.getId()).removeValue();
                Toast.makeText(ListaLlaves.this, "La llave se borro con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Copiado de la llave Publica
         */
        CopyPub = findViewById(R.id.btnCopiarKeyPub);
        CopyPub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyPub = findViewById(R.id.txtKeyPub);

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText",KeyPub.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(ListaLlaves.this, "Llave Publica copiada con éxito", Toast.LENGTH_SHORT).show();
            }
        });

        /**
         * Copiado de la llave Privada
         */
        CopyPriv = findViewById(R.id.btnCopiarKeyPriv);
        CopyPriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyPriv = findViewById(R.id.txtKeyPriv);

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("EditText",KeyPriv.getText().toString());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(ListaLlaves.this, "Llave Privada copiada con éxito", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void ListarDatos() {
        databaseReference.child("Llave").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                llaveList.clear();
                for (DataSnapshot objSnaptshot : snapshot.getChildren()){
                    Llave key = objSnaptshot.getValue(Llave.class);
                    llaveList.add(key);

                    listKeys = findViewById(R.id.lv_listaKeys);
                    arrayAdapterLlave = new ArrayAdapter<Llave>(ListaLlaves.this, android.R.layout.simple_list_item_1, llaveList);
                    listKeys.setAdapter(arrayAdapterLlave);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void limpiarcajas() {
        Description.setText("");
        KeyPub.setText("");
        KeyPriv.setText("");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        KeyPriv = findViewById(R.id.txtKeyPriv);
        outState.putString("KeyPriv",KeyPriv.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        KeyPriv = findViewById(R.id.txtKeyPriv);
        KeyPriv.setText(savedInstanceState.getString("KeyPriv"));
    }
}