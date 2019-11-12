package com.cesde.serviciobanco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText codservicio, descripcion;
    Button iniciar;
    TextView registrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        codservicio = findViewById(R.id.etcodservicio);
        descripcion=findViewById(R.id.etdescripcion);
        iniciar=findViewById(R.id.btniniciarsesion);
        registrar=findViewById(R.id.tvregistrarse);

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registro.class));
            }
        });
    }
}
