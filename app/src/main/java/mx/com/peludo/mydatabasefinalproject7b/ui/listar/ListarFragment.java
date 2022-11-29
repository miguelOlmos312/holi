package mx.com.peludo.mydatabasefinalproject7b.ui.listar;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import mx.com.peludo.mydatabasefinalproject7b.ConexionSQLiteHelper;
import mx.com.peludo.mydatabasefinalproject7b.R;
import mx.com.peludo.mydatabasefinalproject7b.database.Clientes;
import mx.com.peludo.mydatabasefinalproject7b.databinding.FragmentSlideshowBinding;
import mx.com.peludo.mydatabasefinalproject7b.ui.slideshow.SlideshowViewModel;

public class ListarFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    Spinner listaSpinner;
    ArrayList<String> listaPersonasSpinner;  // Es para el spinner
    ArrayList<Clientes> listaObjetosClientes; // Contiene registros de la tabla clientes
    ConexionSQLiteHelper con;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_listar, container, false);

        listaSpinner = (Spinner) view.findViewById(R.id.spListaClientes);

        con = new ConexionSQLiteHelper(view.getContext(),
                "db_clientes", null, 1);
        // Método para llenar el spinner con objetos cliente
        llenarSpinner();

        // Configuramos el spinner
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity(),
                android.R.layout.simple_spinner_item, listaPersonasSpinner);
        listaSpinner.setAdapter(adapter);

        listaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String msg = String.valueOf(listaObjetosClientes.get(i).getClave())+
                        listaObjetosClientes.get(i).getNombre()+
                        listaObjetosClientes.get(i).getDireccion();

                printVentana(msg);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void printVentana(String msg) {

        AlertDialog.Builder vent = new AlertDialog.Builder(getActivity());
        vent.setTitle("Datos del Cliente");
        vent.setMessage(msg);
        vent.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        vent.show();
    }

    /**
     * Método para llenar spinner
     */
    private void llenarSpinner() {
        SQLiteDatabase db = con.getReadableDatabase();
        Clientes cliente = null;
        listaObjetosClientes = new ArrayList<Clientes>();
        // Cursor para leer los registros
        Cursor cursor = db.rawQuery("SELECT * FROM clientes", null);
        // Leemos cursor y los asignamos al arrayAdapter de objetos
        while (cursor.moveToNext()){
            cliente = new Clientes();
            cliente.setClave(cursor.getInt(0));
            cliente.setNombre(cursor.getString(1));
            cliente.setDireccion(cursor.getString(2));
            // Agregar objeto al arraylist de clientes
            listaObjetosClientes.add(cliente);
        }
        // Construir el arraylist tipo String
        obtenerLista();
    }

    /**
     * LLenar el arraylist tipo String con los campos de los objetos
     */
    private void obtenerLista() {
        listaPersonasSpinner = new ArrayList<>();
        // Recorremos array de objetos Clientes y copiamos clave y nombre
        for (int i=0; i < listaObjetosClientes.size(); i++){
            listaPersonasSpinner.add(String.valueOf(listaObjetosClientes.get(i).getClave())+
                    " - "+listaObjetosClientes.get(i).getNombre());
        }
    }
}
