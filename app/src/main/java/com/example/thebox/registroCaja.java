package com.example.thebox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class registroCaja extends AppCompatActivity {
    private View botonRegistro;
    private EditText pais,ciudad,calle,detalle,contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_caja);
        botonRegistro = findViewById(R.id.mandarRegistroCaja);
        pais = findViewById(R.id.paisCaja);
        ciudad = findViewById(R.id.ciudadCaja);
        calle = findViewById(R.id.calleCaja);
        detalle=findViewById(R.id.detalleCaja);
        contraseña = findViewById(R.id.contrasenaCaja);
        GradientDrawable drawableIngresar = (GradientDrawable) botonRegistro.getBackground();
        drawableIngresar.setColor(Color.parseColor("#B388FF"));
    }
    public void registrar(View v){

    }

    public void regresar(View v){
        finish();
    }
    public void leerQR(View v){

    }
}