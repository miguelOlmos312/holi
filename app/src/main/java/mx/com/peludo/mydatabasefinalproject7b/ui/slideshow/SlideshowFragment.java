package mx.com.peludo.mydatabasefinalproject7b.ui.slideshow;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import mx.com.peludo.mydatabasefinalproject7b.ConexionSQLiteHelper;
import mx.com.peludo.mydatabasefinalproject7b.R;
import mx.com.peludo.mydatabasefinalproject7b.databinding.FragmentSlideshowBinding;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    EditText buscarCampoId, buscarCampoNombre, buscarCampoDireccion;
    ConexionSQLiteHelper con;
    private Button btnBuscar, btnEditar, btnBorrar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //Agregar los datos para la parte de la busqueda
        buscarCampoId = (EditText) view.findViewById(R.id.txtBuscarClave);
        buscarCampoNombre = (EditText) view.findViewById(R.id.txtBuscarNombre);
        buscarCampoDireccion = (EditText) view.findViewById(R.id.txtBuscarDireccion);
        btnBuscar = (Button) view.findViewById(R.id.btnBuscar);
        btnEditar = (Button) view.findViewById(R.id.btnEditar);
        btnBorrar = (Button) view.findViewById(R.id.btnBorrar);

        con = new ConexionSQLiteHelper(view.getContext(),
                "db_clientes", null, 1);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consultar();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cambiarCliente();
            }
        });

        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarCliente();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Método para borrar clientes
     */
    private void borrarCliente() {
        SQLiteDatabase db = con.getWritableDatabase();
        //id del cliente que deseamos modificar
        String [] parametros = {buscarCampoId.getText().toString()};
        // Borrar físicamente
        db.delete("clientes", "id"+"=?", parametros);
        Toast.makeText(getView().getContext(), "Cliente borrado", Toast.LENGTH_SHORT).show();
        db.close();
        borrarCampos();
    }

    /**
     * Método para modificar clientes
     */
    private void cambiarCliente() {
        SQLiteDatabase db = con.getWritableDatabase();
        //id del cliente que deseamos modificar
        String [] parametros = {buscarCampoId.getText().toString()};
        // Creamos objeto contenvalues para almacenar los campos que modificaremos: nombre, direccion
        ContentValues valores = new ContentValues();
        valores.put("nombre", buscarCampoNombre.getText().toString());
        valores.put("direccion", buscarCampoDireccion.getText().toString());
        // Escribimos físicamente en la tabla clientes
        db.update("clientes", valores, "id"+"=?", parametros );
        Toast.makeText(getView().getContext(), "Cambios Realizados", Toast.LENGTH_SHORT).show();
        db.close();
        borrarCampos();
    }

    /**
     * Método para consultar clientes
     */
    private void consultar() {
        SQLiteDatabase db = con.getReadableDatabase();

        String [] parametros = {buscarCampoId.getText().toString()};
        String [] campos = {"nombre", "direccion"};

        try {
            Cursor cursor = db.query("clientes", campos, "id"+"=?", parametros,null,null,null);
            cursor.moveToFirst();
            // Imprimir los valores recuperados de la tabla en los campos
            buscarCampoNombre.setText(cursor.getString(0));
            buscarCampoDireccion.setText(cursor.getString(1));
            cursor.close();
        } catch (Exception e){
            Toast.makeText(getView().getContext(), "Cliente No Registrado", Toast.LENGTH_SHORT).show();
            borrarCampos();
        }
    }

    /**
     * Método para borrar campos
     */
    public void borrarCampos(){
        buscarCampoId.setText(null);
        buscarCampoNombre.setText(null);
        buscarCampoDireccion.setText(null);
    }
}