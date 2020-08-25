package com.example.thebox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class OpcionesIniciales extends AppCompatActivity {
    TextView mostrarSaludo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_iniciales);
        mostrarSaludo=findViewById(R.id.mostrarNombreUsuario);
        String saludo = "¡Bienvenido "+UsuarioActual.getUser().getNomUsuario()+" !";
        mostrarSaludo.setText(saludo);
    }
}