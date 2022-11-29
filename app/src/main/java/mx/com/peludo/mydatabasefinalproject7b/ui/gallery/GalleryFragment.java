package mx.com.peludo.mydatabasefinalproject7b.ui.gallery;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import mx.com.peludo.mydatabasefinalproject7b.databinding.FragmentGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    EditText agregarCampoId, agregarCampoNombre, agregarCampoDireccion;
    ConexionSQLiteHelper con;
    private Button btnAgregar;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View viewAgregar = inflater.inflate(R.layout.fragment_gallery, container, false);

        agregarCampoId = (EditText) viewAgregar.findViewById(R.id.txtAgregarID);
        agregarCampoNombre = (EditText) viewAgregar.findViewById(R.id.txtAgregarNombre);
        agregarCampoDireccion = (EditText) viewAgregar.findViewById(R.id.txtAgregarDireccion);
        btnAgregar = (Button) viewAgregar.findViewById(R.id.btnAgregar);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarClientes();
            }
        });

        return viewAgregar;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void registrarClientes(){
        con = new ConexionSQLiteHelper(getView().getContext(),
                "db_clientes", null, 1);
        // Creamos un objeto database y    abrimos la base de datos para escritura
        SQLiteDatabase db = con.getWritableDatabase();
        // Creamos un objeto ContenValues
        ContentValues valores = new ContentValues();
        // Ingresamos al objeto contentvalues los campos y sus valores
        valores.put("id", agregarCampoId.getText().toString());
        valores.put("nombre", agregarCampoNombre.getText().toString());
        valores.put("direccion", agregarCampoDireccion.getText().toString());
        // insertar en la tabla fisicamente
        long Result = db.insert("clientes","id", valores);
        // Tostada
        Toast.makeText(getView().getContext(), "Usuario Registrado Correctamente ... "+
                Result, Toast.LENGTH_SHORT).show();
        // Cerramos la bse de datos
        db.close();
        borrarCampos();
    }

    public void borrarCampos(){
        agregarCampoId.setText(null);
        agregarCampoNombre.setText(null);
        agregarCampoDireccion.setText(null);
    }

}