package mx.com.peludo.mydatabasefinalproject7b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    private String CREAR_TABLA_CLIENTES = "CREATE TABLE clientes (id INTEGER, nombre TEXT,  direccion TEXT)";

    public ConexionSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear tabla de datos
        db.execSQL(CREAR_TABLA_CLIENTES);

   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Borramos la version anterior  de la tabla
        db.execSQL("DROP TABLE IF EXISTS clientes");
        // Creamos la tabla clientes
        onCreate(db);


    }
}
