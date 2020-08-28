package com.example.thebox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class VerificacionCaja extends AppCompatActivity {
    private View botonVerificar;
    private Spinner spinnerCajas;
    private EditText contraCaja;
    private String[] listaInfoCajas;
    private String[] listaIDCajas;
    private String urlRellenarSpinner,urlVerificarContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificacion_caja);
        botonVerificar = findViewById(R.id.botonVerificarCaja);
        GradientDrawable drawableCaja = (GradientDrawable) botonVerificar.getBackground();
        drawableCaja.setColor(Color.parseColor("#B388FF"));
        contraCaja=findViewById(R.id.contraVerificacionCaja);
        spinnerCajas = findViewById(R.id.spinnerCajas);
        listaInfoCajas = rellenarSpinner();
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listaInfoCajas);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCajas.setAdapter(aa);
    }
    private String[] rellenarSpinner(){
        String[] resultado;
        try {
            String[] datos = new String[]{//FALTA PHP
                    "manejarCaja",
                    urlRellenarSpinner,
                    UsuarioActual.getUser().getNomUsuario()
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
            String[] myData= resultado[0].trim().split("\\n");
            ArrayList<String> infoImp = new ArrayList<>();
            for(int i=0;i<myData.length;i++){
                String strInfoCaja = myData[i].replace(","," |");
                infoImp.add(strInfoCaja);//aqui debo ver que lo que trae el php se genere el id caja en usuario
            }
            return infoImp.toArray(new String[0]);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
    public void administrarCaja(View v){//FALTA PHP
        String[] resultado;
        String idCajaEscogidaSpinner =listaIDCajas[spinnerCajas.getSelectedItemPosition()];
        String contra = contraCaja.getText().toString();
        if(spinnerCajas.getSelectedItem().toString().isEmpty() || contra.isEmpty()){
            Toast.makeText(VerificacionCaja.this, "Escoja una caja o ingrese contraseña.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String[] datos = new String[]{
                    "verificarCaja",
                    urlVerificarContra,
                    idCajaEscogidaSpinner,
                    contra
            };
            AsyncQuery async = new AsyncQuery();
            resultado = async.execute(datos).get();
            String[] myData = resultado[0].trim().split("\\n")[0].split(",");
            ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
            if (infoImp.get(0).equals("denegado")) {
                Toast.makeText(VerificacionCaja.this, "Usuario no tiene acceso a esta caja.", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(this, QRGeneral.class);
            startActivity(intent);
        }catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void regresar(View v){
        finish();
    }
}