package com.cesde.serviciobanco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity {
    EditText codservicio, descripcion;
    Button registrar, buscar,actualizar,eliminar;
    FirebaseFirestore db;
    String idauto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        codservicio = findViewById(R.id.etcodservicior);
        descripcion = findViewById(R.id.etdescripcionr);
        registrar = findViewById(R.id.btnregistrarr);
        buscar = findViewById(R.id.btnbuscarr);
        actualizar=findViewById(R.id.btnactualizarr);
        eliminar=findViewById(R.id.btneliminarr);

        db = FirebaseFirestore.getInstance();

        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idauto ="";
                db.collection("servicio")
                     .whereEqualTo("codservicio", codservicio.getText().toString())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful())
                                {
                                    for (QueryDocumentSnapshot document : task.getResult())
                                    {
                                        idauto = document.getId();
                                        codservicio.setText(document.getData().get("codservicio").toString());
                                        descripcion.setText(document.getData().get("descripcion").toString());
                                    }
                                    if (idauto.isEmpty())
                                    {
                                        Toast.makeText(getApplicationContext(), "Servicio no existe",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Log.d("servicio", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }
        });
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Guardar
                Map<String, Object> cliente = new HashMap<>();
                cliente.put("codservicio",codservicio.getText().toString());
                cliente.put("descripcion",descripcion.getText().toString());

                db.collection("servicio")
                        .add(cliente)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                Toast.makeText(getApplicationContext(), "Servicio agregado correctamente",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error adding document", e);
                                Toast.makeText(getApplicationContext(), "Error de conexion a la base de datos",Toast.LENGTH_SHORT).show();

                            }
                        });
                //Fin guardar
            }
        });
        //Actualizar
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> acliente = new HashMap<>();
                acliente.put("codservicio",codservicio.getText().toString());
                acliente.put("descripcion",descripcion.getText().toString());

                db.collection("servicio").document(idauto)
                        .set(acliente)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Servici actualizado correctamente",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Error de conexión",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        //Eliminar
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("servicio").document(idauto)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Log.d("cliente", "DocumentSnapshot successfully deleted!");
                                Toast.makeText(getApplicationContext(),"Servicio borrado correctamente...",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Prods", "Error deleting document", e);
                            }
                        });
            }
        });
    }
}


