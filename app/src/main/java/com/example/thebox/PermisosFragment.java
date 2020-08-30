package com.example.thebox;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;


public class PermisosFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private EditText nombreUsuario;
    private Spinner spinnerAccion;
    private String url_manejoAccesos = "https://lab6-chiquito.000webhostapp.com/manejarAccesoCaja.php" ;


    public PermisosFragment() {
        // Required empty public constructor
    }

    public static PermisosFragment newInstance(String param1, String param2) {
        PermisosFragment fragment = new PermisosFragment();
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
        View view = inflater.inflate(R.layout.fragment_permisos, container, false);
        nombreUsuario=view.findViewById(R.id.usuarioOrden);
        spinnerAccion = view.findViewById(R.id.spinnerComando);
        String[] opciones = new String[]{"Agregar","Eliminar"};
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,opciones);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAccion.setAdapter(aa);
        Button botonComando = (Button) view.findViewById(R.id.ejecutarOrden);
        GradientDrawable drawableCaja = (GradientDrawable) botonComando.getBackground();
        drawableCaja.setColor(Color.parseColor("#B388FF"));
        botonComando.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {String[] resultado;
                String nomUs = nombreUsuario.getText().toString();
                String comando = spinnerAccion.getSelectedItem().toString();
                if(nomUs.isEmpty()){
                    Toast.makeText(getActivity(), "Ingrese información completa.", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    String[] datos = new String[]{
                            "manipularAccesos",
                            url_manejoAccesos,
                            nomUs,
                            comando,
                            UsuarioActual.getIdCajaActualenRevision()
                    };
                    AsyncQuery async = new AsyncQuery();
                    resultado = async.execute(datos).get();
                    System.out.println(resultado[0]);
                    String[] myData= resultado[0].trim().split("\\n")[0].split(",");
                    ArrayList<String> infoImp = new ArrayList<>(Arrays.asList(myData));
                    if (!infoImp.get(0).isEmpty()){
                        Toast.makeText(getActivity(), infoImp.get(0), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Ocurrió un error.", Toast.LENGTH_SHORT).show();
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
}