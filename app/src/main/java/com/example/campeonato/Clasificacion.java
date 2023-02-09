package com.example.campeonato;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Clasificacion extends AppCompatActivity {

    ListView lv;
    SQLiteDatabase db;
    SQLiteHelper helper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clasificacion);

        lv = findViewById(R.id.lvClasi);

        consultaEquipos();
    }

    private void consultaEquipos() {
        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(EstructuraBBDD.EstructuraEquipo.TABLE_NAME_EQUIPOS, null,
                null, null, null, null, "ASC");
        //adaptamos el cursor a nuestro ListView
        String[] from = {EstructuraBBDD.EstructuraEquipo.COLUMN_NAME_NOMBRE,
                EstructuraBBDD.EstructuraEquipo.COLUMN_NAME_CIUDAD,
                EstructuraBBDD.EstructuraEquipo.COLUMN_PUNTOS_ACUMULADOS,
                EstructuraBBDD.EstructuraEquipo.COLUMN_FOTO};
        int[] to = {R.id.textView3, R.id.textView4, R.id.textView5, R.id.imageView};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.activity_lista, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

    }
}