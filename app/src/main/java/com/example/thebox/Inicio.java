package com.example.thebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class Inicio extends AppCompatActivity {
    private EditText etUser,etContra;
    private View botRegistrar,botIngresar;
    private String login = "https://lab6-chiquito.000webhostapp.com/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUser = findViewById(R.id.usuario);
        etContra = findViewById(R.id.contrasena);
        botRegistrar = findViewById(R.id.botonRegistrar);
        botIngresar = findViewById(R.id.botonIngresar);
        GradientDrawable drawableRegistrar = (GradientDrawable) botRegistrar.getBackground();
        drawableRegistrar.setColor(Color.parseColor("#82B1FF"));
        GradientDrawable drawableIngresar = (GradientDrawable) botIngresar.getBackground();
        drawableIngresar.setColor(Color.parseColor("#B388FF"));
    }
    public void ingresar(View v){
        String[] resultado;
        String nomUs = etUser.getText().toString();
        String contra = etContra.getText().toString();
        if(nomUs.isEmpty() || contra.isEmpty()){
            Toast.makeText(Inicio.this, "Ingrese información solicitada.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String[] datos = new String[]{
                    "login",
                    login,
                    nomUs,
                    contra
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
            String[] myData= resultado[0].trim().split("\\n")[0].split(",");
            ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
            if (infoImp.get(0).equals("denegado")){
                Toast.makeText(Inicio.this, "Usuario no registrado o datos ingresados incorrectos.", Toast.LENGTH_SHORT).show();
                return;
            }
            UsuarioActual actUsuario = new UsuarioActual(infoImp.get(1), infoImp.get(2),infoImp.get(3),infoImp.get(4));
            UsuarioActual.setUser(actUsuario);

            Intent intent=new Intent(this,OpcionesIniciales.class);

            startActivity(intent);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
    public void irRegistro(View v){
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }
}