package com.example.thebox;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Registro extends AppCompatActivity {
    private EditText edNombre,edApellido,edCorreo,edUsuario,edContraseña;
    private Button botonRegistrarse;
    private String register = "https://lab6-chiquito.000webhostapp.com/registro.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edNombre = findViewById(R.id.nombre);
        edApellido = findViewById(R.id.apellido);
        edCorreo = findViewById(R.id.correo);
        edUsuario = findViewById(R.id.usuario);
        edContraseña = findViewById(R.id.contrasena);
        botonRegistrarse = findViewById(R.id.botonRegistrarse);
        GradientDrawable drawableIngresar = (GradientDrawable) botonRegistrarse.getBackground();
        drawableIngresar.setColor(Color.parseColor("#B388FF"));
    }
    public void registrarse(View v) {
        String[] resultado;
        String nombre = edNombre.getText().toString();
        String apellido = edApellido.getText().toString();
        String correo = edCorreo.getText().toString();
        String usuario = edUsuario.getText().toString();
        String contrasena = edContraseña.getText().toString();
        if(nombre.isEmpty() || apellido.isEmpty()||correo.isEmpty()||usuario.isEmpty()||contrasena.isEmpty()){
            Toast.makeText(Registro.this, "Ingrese información solicitada.", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] datos = new String[]{
                "registrar",
                register,
                usuario,
                nombre,
                apellido,
                correo,
                contrasena
        };
        AsyncQuery async = new AsyncQuery();
        try {
            resultado = async.execute(datos).get();
            String[] myData= resultado[0].trim().split("\\n")[0].split(",");
            ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
            if (infoImp.get(0).equals("denegado")){
                Toast.makeText(Registro.this, "Usuario ya se encuentra registrado.", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(Registro.this, "Usuario registrado exitosamente.", Toast.LENGTH_SHORT).show();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void regresar(View v){
        finish();
    }


}