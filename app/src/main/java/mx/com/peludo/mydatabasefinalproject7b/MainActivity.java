package mx.com.peludo.mydatabasefinalproject7b;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import mx.com.peludo.mydatabasefinalproject7b.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    EditText buscarCampoId, buscarCampoNombre, buscarCampoDireccion;
    ConexionSQLiteHelper con;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Salir de la app
                finish();
            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //Agregar los datos para la parte de la busqueda
        buscarCampoId = (EditText) findViewById(R.id.txtBuscarClave);
        buscarCampoNombre = (EditText) findViewById(R.id.txtBuscarNombre);
        buscarCampoDireccion = (EditText) findViewById(R.id.txtBuscarDireccion);

        con = new ConexionSQLiteHelper(this,
                "db_clientes", null, 1);

        // Elimina lo oscuro de los botones
        navigationView.setItemIconTintList(null);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_listarclientes)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Método para borrar clientes
     */
    public void borrarCliente() {
        SQLiteDatabase db = con.getWritableDatabase();
        //id del cliente que deseamos modificar
        String [] parametros = {buscarCampoId.getText().toString()};
        // Borrar físicamente
        db.delete("clientes", "id"+"=?", parametros);
        Toast.makeText(this, "Cliente borrado", Toast.LENGTH_SHORT).show();
        db.close();
        borrarCampos();
    }

    /**
     * Método para modificar clientes
     */
    public void cambiarCliente() {
        SQLiteDatabase db = con.getWritableDatabase();
        //id del cliente que deseamos modificar
        String [] parametros = {buscarCampoId.getText().toString()};
        // Creamos objeto contenvalues para almacenar los campos que modificaremos: nombre, direccion
        ContentValues valores = new ContentValues();
        valores.put("nombre", buscarCampoNombre.getText().toString());
        valores.put("direccion", buscarCampoDireccion.getText().toString());
        // Escribimos físicamente en la tabla clientes
        db.update("clientes", valores, "id"+"=?", parametros );
        Toast.makeText(this, "Cambios Realizados", Toast.LENGTH_SHORT).show();
        db.close();
        borrarCampos();
    }

    /**
     * Método para consultar clientes
     */
    public void consultar() {
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
            Toast.makeText(this, "Cliente No Registrado", Toast.LENGTH_SHORT).show();
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