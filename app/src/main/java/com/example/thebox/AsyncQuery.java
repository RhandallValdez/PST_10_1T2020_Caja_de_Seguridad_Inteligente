package com.example.thebox;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AsyncQuery extends AsyncTask<String[],Void,String[]> {
    @Override
    protected String[] doInBackground(String[]... datos) {
        String[] totalResultadoSQL = null;
        String type = datos[0][0];
        String login_url = datos[0][1];
        if(type.equals("login")){
        try {
            String usuarioIngresado = datos[0][2];
            String contraIngresada = datos[0][3];
            String SQL = usuarioIngresado+","+contraIngresada;
            HttpURLConnection conexion = outputInformacion(SQL,login_url);
            totalResultadoSQL = inputInformacion(conexion);
        } catch (MalformedURLException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        else if(type.equals("registrar")){
        try {
            String usuario = datos[0][2];
            String nombres = datos[0][3];
            String apellidos = datos[0][4];
            String correo = datos[0][5];
            String contrasena = datos[0][6];
            String SQL = usuario+","+nombres + "," + apellidos + "," + correo +","+ contrasena;
            HttpURLConnection conexion = outputInformacion(SQL,login_url);
            totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("verificarAdmin")){
            try {
                String nombreUsuario = datos[0][2];
                HttpURLConnection conexion = outputInformacion(nombreUsuario,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e ) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("verificarQR")){

            try {
                String usuario = datos[0][2];
                String infoQR = datos[0][3];

                String SQL = usuario+","+infoQR ;
                HttpURLConnection conexion = outputInformacion(SQL,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("manejarCaja")){
            try {
                String usuario = datos[0][2];
                String SQL = usuario;
                HttpURLConnection conexion = outputInformacion(SQL,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("registrarCaja")){
            try {
                String paisCaja = datos[0][2];
                String ciudadCaja = datos[0][3];
                String calleCaja = datos[0][4];
                String detalleCaja = datos[0][5];
                String contrasenaCaja = datos[0][6];
                String infoQR = datos [0][7];
                String SQL = paisCaja+","+ciudadCaja + "," + calleCaja + "," + detalleCaja +","+ contrasenaCaja + "," + infoQR;
                HttpURLConnection conexion = outputInformacion(SQL,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("verificarCaja")){
            try {
                String idCaja = datos[0][2];
                String contra = datos[0][3];
                String SQL = idCaja+","+contra;
                HttpURLConnection conexion = outputInformacion(SQL,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("manipularAccesos")){
            try {
                String usuario = datos[0][2];
                String comando = datos[0][3];
                String idCaja = datos[0][4];
                String SQL = usuario+","+comando+","+idCaja;
                HttpURLConnection conexion = outputInformacion(SQL,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("llenarTablaAccesos")){
            try {
                String idCaja = datos[0][2];
                String fechaInicio = datos[0][3];
                String fechaFin = datos[0][4];
                String SQL = idCaja+","+fechaInicio+","+fechaFin;
                HttpURLConnection conexion = outputInformacion(SQL,login_url);
                totalResultadoSQL = inputInformacion(conexion);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return totalResultadoSQL;
    }
    public HttpURLConnection outputInformacion(String SQL,String login_url) throws IOException{
        URL url = new URL(login_url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        OutputStream outputStream = httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        String tablaPost = URLEncoder.encode("SQL", "UTF-8") + "=" + URLEncoder.encode(SQL, "UTF-8");
        bufferedWriter.write(tablaPost);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        return httpURLConnection;
    }
    public String[] inputInformacion(HttpURLConnection httpURLConnection) throws IOException{
        InputStream iStr = httpURLConnection.getInputStream();
        BufferedReader bR = new BufferedReader(new InputStreamReader(iStr,"UTF-8"));
        String resultado="";
        String line="";

        while((line = bR.readLine()) != null){
            resultado += line + System.getProperty("line.separator") ;
        }
        bR.close();
        iStr.close();
        httpURLConnection.disconnect();

        return  new String[]{resultado};
    }
}
