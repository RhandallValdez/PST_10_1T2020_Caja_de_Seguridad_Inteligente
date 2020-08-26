package com.example.thebox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class OpcionesIniciales extends AppCompatActivity {
    private TextView mostrarSaludo;
    private View manejoCaja,registroCaja,escanear;
    private String verificacionAdmin = "https://lab6-chiquito.000webhostapp.com/verificacionAdmin.php";
    private String verificacionQR = "https://lab6-chiquito.000webhostapp.com/verificacionQR.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_iniciales);
        mostrarSaludo=findViewById(R.id.mostrarNombreUsuario);
        String saludo = "¡Bienvenido "+UsuarioActual.getUser().getNomUsuario()+" !";
        mostrarSaludo.setText(saludo);
        manejoCaja = findViewById(R.id.administrar);
        registroCaja = findViewById(R.id.registrarCaja);
        escanear = findViewById(R.id.escanearQR);
        GradientDrawable drawableManejo = (GradientDrawable) manejoCaja.getBackground();
        drawableManejo.setColor(Color.TRANSPARENT);
        GradientDrawable drawableRegistro = (GradientDrawable) registroCaja.getBackground();
        drawableRegistro.setColor(Color.TRANSPARENT);
        GradientDrawable drawableEscanear = (GradientDrawable) escanear.getBackground();
        drawableEscanear.setColor(Color.TRANSPARENT);
    }
    public void regresar(View v){
        finish();
    }
    public void registrarCaja(View v){
        String[] resultado;
        try {
            String[] datos = new String[]{
                    "verificarAdmin",
                    verificacionAdmin,
                    UsuarioActual.getUser().getNomUsuario()
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
            System.out.println(resultado[0]);
            String[] myData= resultado[0].trim().split("\\n")[0].split(",");
            ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
            if (infoImp.get(0).equals("denegado")){
                Toast.makeText(OpcionesIniciales.this, "Usuario no tiene permisos de administrador.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent=new Intent(this,registroCaja.class);
            startActivity(intent);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void accederCaja(View v){
        Intent intent=new Intent(this,QRGeneral.class);
        startActivity(intent);
    }
    public void leerQR(View v){
        scanCode();
    }
    public void  scanCode(){
        IntentIntegrator intergrator = new IntentIntegrator(this);
        intergrator.setCaptureActivity(CaptureAct.class);
        intergrator.setOrientationLocked(false);
        intergrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        intergrator.setPrompt("Escaneando codigo");
        intergrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result != null){
            if (result.getContents() != null){
                String[] resultado;
                try {
                    String[] datos = new String[]{
                            "verificarQR",
                            verificacionQR,
                            UsuarioActual.getUser().getNomUsuario(),
                            result.getContents()

                    };
                    AsyncQuery async = new AsyncQuery();
                    resultado = async.execute(datos).get();
                    System.out.println(resultado[0]);
                    String[] myData= resultado[0].trim().split("\\n")[0].split(",");
                    ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
                    if (infoImp.get(0).equals("acceso")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Acceso Exitoso");
                        builder.setTitle("Resultado de Escaneado");
                        builder.setPositiveButton("Escanear de Nuevo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                scanCode();
                            }
                        }).setNegativeButton("Terminado", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }else {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                        builder1.setMessage("Acceso Negado");
                        builder1.setTitle("Resultado de Escaneado");
                        builder1.setPositiveButton("Escanear de Nuevo", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                scanCode();
                            }
                        }).setNegativeButton("Terminado", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.cancel();
                            }
                        });
                        AlertDialog dialog = builder1.create();
                        dialog.show();
                    }

                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        }else{
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

}