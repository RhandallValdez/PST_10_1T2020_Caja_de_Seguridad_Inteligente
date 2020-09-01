package com.example.thebox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class registroCaja extends AppCompatActivity {
    private View botonRegistro;
    private EditText pais,ciudad,calle,detalle,contrasena;
    private String url = "https://lab6-chiquito.000webhostapp.com/registroCaja1.php";
    String TAG = "GenerateQRCode";
    String infoQR, userName;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    Random r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_caja);
        botonRegistro = findViewById(R.id.mandarRegistroCaja);
        pais = findViewById(R.id.paisCaja);
        ciudad = findViewById(R.id.ciudadCaja);
        calle = findViewById(R.id.calleCaja);
        detalle=findViewById(R.id.detalleCaja);
        contrasena = findViewById(R.id.contrasenaCaja);
        GradientDrawable drawableIngresar = (GradientDrawable) botonRegistro.getBackground();
        drawableIngresar.setColor(Color.parseColor("#B388FF"));

        ActivityCompat.requestPermissions(registroCaja.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        ActivityCompat.requestPermissions(registroCaja.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

    }
    public void registrar(View v){

        String[] resultado;
        String paisCaja = pais.getText().toString();
        String ciudadCaja = ciudad.getText().toString();
        String calleCaja = calle.getText().toString();
        String detalleCaja = detalle.getText().toString();
        String contrasenaCaja = contrasena.getText().toString();
        if(paisCaja.isEmpty() || ciudadCaja.isEmpty()||calleCaja.isEmpty()||detalleCaja.isEmpty()||contrasenaCaja.isEmpty()){
            Toast.makeText(registroCaja.this, "Complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }
        r = new Random();
        userName = UsuarioActual.getUser().getNomUsuario();
        infoQR = userName + String.valueOf(r.nextInt(10001));

        String[] datos = new String[]{
                "registrarCaja",
                url,
                paisCaja,
                ciudadCaja,
                calleCaja,
                detalleCaja,
                contrasenaCaja,
                infoQR,
                userName
        };
        AsyncQuery async = new AsyncQuery();
        try {
            resultado = async.execute(datos).get();
            String[] myData= resultado[0].trim().split("\\n")[0].split(",");
            ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
            if (infoImp.get(0).equals(" denegado ")){
                Toast.makeText(registroCaja.this, "La ubicación ya existía y el registro de la caja fue existoso.", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(registroCaja.this, infoImp.get(0), Toast.LENGTH_SHORT).show();
            }
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = width < height ? width : height;
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(
                    infoQR, null,
                    QRGContents.Type.TEXT,
                    smallerDimension);
            try {
                bitmap = qrgEncoder.encodeAsBitmap();
            } catch (WriterException e) {
                Log.v(TAG, e.toString());
            }


            FileOutputStream outputStream = null;
            File file = Environment.getExternalStorageDirectory();
            File dir = new File(file.getPath() + "/QRCode/");
            dir.mkdirs();

            String filename = String.format("%d.jpeg", System.currentTimeMillis());
            File outfile = new File(dir, filename);

            try {
                outputStream = new FileOutputStream(outfile);
                //Toast.makeText(getApplicationContext(),infoQR,Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            try {
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
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
    public void leerQR(View v){

    }
}