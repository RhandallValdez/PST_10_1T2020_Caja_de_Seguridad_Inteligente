package com.example.thebox;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutionException;


public class AccesosFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button ButFechaInicio,ButFechaFin,ButEjecutar;
    private TextView etFin,etInicio;
    private TableLayout table;
    private String url_llenarTabla = "https://lab6-chiquito.000webhostapp.com/tablaAccesos.php";


    private String mParam1;
    private String mParam2;

    public AccesosFragment() {
        // Required empty public constructor
    }

    public static AccesosFragment newInstance(String param1, String param2) {
        AccesosFragment fragment = new AccesosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accesos, container, false);
        table = view.findViewById(R.id.tablaConsulta);
        etFin = view.findViewById(R.id.textViewFin);
        etInicio = view.findViewById(R.id.textViewInicio);
        ImageButton regresar = view.findViewById(R.id.volverVerificarCaja1);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        ButFechaInicio = view.findViewById(R.id.fechaInicio);
        GradientDrawable drawableInicio = (GradientDrawable) ButFechaInicio.getBackground();
        drawableInicio.setColor(Color.parseColor("#82B1FF"));

        ButFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        final String selectedDate = year+"-"+(month+1) + "-" + day;
                        etInicio.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

            }
        });

        ButFechaFin = view.findViewById(R.id.fechaFin);
        GradientDrawable drawableFin = (GradientDrawable) ButFechaFin.getBackground();
        drawableFin.setColor(Color.parseColor("#82B1FF"));

        ButFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        final String selectedDate = year+"-"+(month+1) + "-" + day;
                        etFin.setText(selectedDate);
                    }
                });
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

            }
        });

        ButEjecutar = view.findViewById(R.id.llenarTabla);
        GradientDrawable drawableLlenar = (GradientDrawable) ButEjecutar.getBackground();
        drawableLlenar.setColor(Color.parseColor("#B388FF"));

        ButEjecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] resultado = null;
                String fechaInicio = etInicio.getText().toString();
                String fechaFin = etFin.getText().toString();
                if(fechasIncorrectas(fechaInicio,fechaFin)){
                    Toast.makeText(getActivity(), "Ingrese fechas correctamente.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fechaInicio.isEmpty()||fechaFin.isEmpty()){
                    Toast.makeText(getActivity(), "Ingrese fechas.", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String[] datos = new String[]{
                            "llenarTablaAccesos",
                            url_llenarTabla,
                            UsuarioActual.getIdCajaActualenRevision(),
                            fechaInicio +" 00:00:00",
                            fechaFin+" 23:59:59"
                    };
                    AsyncQuery async = new AsyncQuery();
                    resultado = async.execute(datos).get();
                    System.out.println(resultado[0]);
                    if(resultado[0].equals("Tabla vacía")){
                        Toast.makeText(getActivity(), "Sin resultados.", Toast.LENGTH_SHORT).show();
                    }
                    String resultadoTabla = resultado[0].trim();
                    String[] myData= resultadoTabla.split("\\n");
                    ArrayList<ArrayList<String>> infoQuery = new ArrayList<>();
                    for(int i=0;i<myData.length;i++){
                        String[] filaStrings = myData[i].split(",");
                        ArrayList<String> fila = new ArrayList<String>(Arrays.asList(filaStrings));
                        infoQuery.add(fila);
                    }
                    table.removeAllViews();
                    for(int x=0;x<infoQuery.size();x++){
                        TableRow tbrow = new TableRow(getActivity());
                        for(int y=0;y<infoQuery.get(x).size();y++){
                            TextView tv = new TextView(getActivity());
                            tv.setText(infoQuery.get(x).get(y));
                            if(x==0){
                                tv.setTextColor(Color.WHITE);
                                tv.setBackgroundColor(Color.rgb(157,186,213));
                            }else{
                                tv.setTextColor(Color.GRAY);
                                tv.setBackgroundColor(Color.rgb(250,243,221));
                            }
                            tv.setTextSize(12);
                            tv.setGravity(Gravity.CENTER);
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                            tv.setPadding(15,0,15,0);
                            tbrow.addView(tv);
                        }

                        table.addView(tbrow, new TableLayout.LayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)));
                    }
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    public boolean fechasIncorrectas(String fechaInicio,String fechaFin){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdformat = new SimpleDateFormat("MM-dd-yyyy");
        boolean FinMenor = true;
        try {
            Date d1 = sdformat.parse(fechaInicio);
            Date d2 = sdformat.parse(fechaFin);
            if(d2.compareTo(d1)>=0){
                FinMenor = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return FinMenor;

    }
}