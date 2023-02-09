package com.example.campeonato;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ModificarEquipo extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteHelper helper;
    TextView editTextNombre, editTextCiudad, editTextPuntos;
    Button botonInsertar, botonEditar, botonEliminar;
    ImageView imgViewFoto;
    String nombre, ciudad, puntos;
    ListView lv;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_equipo);
        botonInsertar = findViewById(R.id.btnInsertar);
        botonEditar = findViewById(R.id.btnEditar);
        botonEliminar = findViewById(R.id.btnEliminar);
        editTextNombre = findViewById(R.id.editTxtNombre);
        editTextCiudad = findViewById(R.id.editTxtCiudad);
        editTextPuntos = findViewById(R.id.editTxtPuntos);
        lv = findViewById(R.id.lvEquipos);

        botonInsertar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNombre.getText().toString().isEmpty() && !editTextCiudad.getText().toString().isEmpty() && !editTextPuntos.getText().toString().isEmpty()){
                    nombre = editTextNombre.getText().toString();
                    ciudad = editTextCiudad.getText().toString();
                    puntos = editTextPuntos.getText().toString();
                    insertar(nombre, ciudad, puntos);
                    consultaEquipos();
                }else{
                    Toast.makeText(ModificarEquipo.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });
        botonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNombre.getText().toString().isEmpty()){
                    nombre = editTextNombre.getText().toString();
                    ciudad = editTextCiudad.getText().toString();
                    puntos = editTextPuntos.getText().toString();
                    modificarPorNombre(nombre, ciudad, puntos);
                    consultaEquipos();
                }else{
                    Toast.makeText(ModificarEquipo.this, "Debes rellenar el nombre", Toast.LENGTH_SHORT).show();
                }

            }
        });
        botonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextNombre.getText().toString().isEmpty()){
                    nombre = editTextNombre.getText().toString();
                    eliminar(nombre);
                    consultaEquipos();
                }else{
                    Toast.makeText(ModificarEquipo.this, "Debes rellenar el nombre", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void eliminar(String nombre) {
        db.execSQL("DELETE FROM "+ EstructuraBBDD.EstructuraEquipo.TABLE_NAME_EQUIPOS + " WHERE nombre= '"+nombre+"'");

        editTextNombre.setText("");
        editTextCiudad.setText("");
        editTextPuntos.setText("");
    }

    public void insertar(String nombre, String ciudad, String puntos) {
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("ciudad", ciudad);
        values.put("puntos", puntos);
        values.put("foto", R.drawable.manzanares);
        db.insert("equipos",null, values);

        editTextNombre.setText("");
        editTextCiudad.setText("");
        editTextPuntos.setText("");
    }

    private void consultaEquipos() {
        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(EstructuraBBDD.EstructuraEquipo.TABLE_NAME_EQUIPOS, null,
                        null, null, null, null, null);
        //adaptamos el cursor a nuestro ListView
        String[] from = {EstructuraBBDD.EstructuraEquipo.COLUMN_NAME_NOMBRE,
                EstructuraBBDD.EstructuraEquipo.COLUMN_NAME_CIUDAD,
                EstructuraBBDD.EstructuraEquipo.COLUMN_PUNTOS_ACUMULADOS,
                EstructuraBBDD.EstructuraEquipo.COLUMN_FOTO};
        int[] to = {R.id.textView3, R.id.textView4, R.id.textView5, R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.activity_lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

    }


    public void modificarPorNombre(String nombre, String ciudad, String puntos) {
        db.execSQL("UPDATE "+ EstructuraBBDD.EstructuraEquipo.TABLE_NAME_EQUIPOS + " SET nombre= '"+nombre+"', ciudad= '"+ciudad+"', puntos= '"+puntos+"' WHERE nombre= '"+nombre+"'");

        editTextNombre.setText("");
        editTextCiudad.setText("");
        editTextPuntos.setText("");
    }
}