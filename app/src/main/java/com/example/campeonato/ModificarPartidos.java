package com.example.campeonato;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ModificarPartidos extends AppCompatActivity {
    SQLiteDatabase db;
    SQLiteHelper helper;
    EditText editTxtEquipo1, editTxtEquipo2, editTxtPuntosLoc, editTxtPuntosVis;
    Button btnInsertarPar, btnEditarPar, btnEliminarPar;
    ListView lv;
    String equipoLoc, equipoVis, puntosloc, puntosVis, fecha;
    Date c = Calendar.getInstance().getTime();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_partidos);
        editTxtEquipo1 = findViewById(R.id.editTxtEquipo1);
        editTxtEquipo2 = findViewById(R.id.editTxtEquipo2);
        editTxtPuntosLoc = findViewById(R.id.editTxtPuntosLoc);
        editTxtPuntosVis = findViewById(R.id.editTxtPuntosVis);
        btnInsertarPar = findViewById(R.id.btnInsertarPar);
        btnEditarPar = findViewById(R.id.btnEditarPar);
        btnEliminarPar = findViewById(R.id.btnEliminarPar);
        lv = findViewById(R.id.lv);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        fecha = df.format(c);


        btnInsertarPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTxtEquipo1.getText().toString().isEmpty() && !editTxtEquipo2.getText().toString().isEmpty() && !editTxtPuntosLoc.getText().toString().isEmpty() && !editTxtPuntosVis.getText().toString().isEmpty()){
                    equipoLoc = editTxtEquipo1.getText().toString();
                    equipoVis = editTxtEquipo2.getText().toString();
                    puntosloc = editTxtPuntosLoc.getText().toString();
                    puntosVis = editTxtPuntosVis.getText().toString();
                    insertar(equipoLoc, equipoVis, puntosloc, puntosVis, fecha);
                    consultaEquipos();
                }else{
                    Toast.makeText(ModificarPartidos.this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnEditarPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTxtEquipo1.getText().toString().isEmpty() && !editTxtEquipo2.getText().toString().isEmpty()){
                    equipoLoc = editTxtEquipo1.getText().toString();
                    equipoVis = editTxtEquipo2.getText().toString();
                    puntosloc = editTxtPuntosLoc.getText().toString();
                    puntosVis = editTxtPuntosVis.getText().toString();
                    modificarPorNombre(equipoLoc, equipoVis, puntosloc, puntosVis);
                    consultaEquipos();
                }else{
                    Toast.makeText(ModificarPartidos.this, "Debes rellenar el nombre de los equipos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnEliminarPar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTxtEquipo1.getText().toString().isEmpty()){
                    equipoLoc = editTxtEquipo1.getText().toString();
                    equipoVis = editTxtEquipo2.getText().toString();
                    eliminar(equipoLoc, equipoVis);
                    consultaEquipos();
                }else{
                    Toast.makeText(ModificarPartidos.this, "Debes rellenar los nombres de los equipos", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void insertar(String equipoLoc, String equipoVis, String puntosloc, String puntosVis, String fecha) {
        ContentValues values = new ContentValues();
        values.put("equipo1", equipoLoc);
        values.put("equipo2", equipoVis);
        values.put("puntuacion_local", puntosloc);
        values.put("puntuacion_visitante", puntosVis);
        values.put("fecha", fecha);
        db.insert("partidos",null, values);

        editTxtEquipo1.setText("");
        editTxtEquipo2.setText("");
        editTxtPuntosLoc.setText("");
        editTxtPuntosVis.setText("");
    }

    public void eliminar(String equipo1, String equipo2) {
        db.execSQL("DELETE FROM "+ EstructuraBBDD.EstructuraPartidos.TABLE_NAME_PARTIDOS + " WHERE equipo1= '"+equipo1+"' && equipo2= '"+equipo2+"'");

        editTxtEquipo1.setText("");
        editTxtEquipo2.setText("");
        editTxtPuntosLoc.setText("");
        editTxtPuntosVis.setText("");
    }

    private void consultaEquipos() {
        helper = new SQLiteHelper(this);
        db = helper.getReadableDatabase();

        Cursor cursor = db.query(EstructuraBBDD.EstructuraPartidos.TABLE_NAME_PARTIDOS, null,
                null, null, null, null, null);
        //adaptamos el cursor a nuestro ListView
        String[] from = {EstructuraBBDD.EstructuraPartidos.COLUMN_NAME_EQUIPO1,
                EstructuraBBDD.EstructuraPartidos.COLUMN_NAME_EQUIPO2,
                EstructuraBBDD.EstructuraPartidos.COLUMN_NAME_PUNTUACION_LOCAL,
                EstructuraBBDD.EstructuraPartidos.COLUMN_NAME_PUNTUACION_VISITANTE,
                EstructuraBBDD.EstructuraPartidos.COLUMN_NAME_FECHA};
        int[] to = {R.id.textEquipo1, R.id.textEquipo2, R.id.textPuntEquipo1, R.id.textPuntEquipo2, R.id.textFecha};

        SimpleCursorAdapter adaptador = new SimpleCursorAdapter(this, R.layout.activity_lista_partidos, cursor, from, to, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        lv.setAdapter(adaptador);

    }

    public void modificarPorNombre(String equipo1, String equipo2, String puntosLoc, String puntosVis) {
        db.execSQL("UPDATE "+ EstructuraBBDD.EstructuraPartidos.TABLE_NAME_PARTIDOS + " SET equipo1= '"+equipo1+"', equipo2= '"+equipo2+"', puntuacion_local= '"+puntosLoc+"', puntuacion_visitante= '"+puntosVis+"' WHERE equipo1= '"+equipo1+"' && equipo2= '"+equipo2+"'");

        editTxtEquipo1.setText("");
        editTxtEquipo2.setText("");
        editTxtPuntosLoc.setText("");
        editTxtPuntosVis.setText("");
    }
}