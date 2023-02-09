package com.example.campeonato;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button modEquipo, modPartidos, clasificacion, activarSonido, desactivarSonido;
    SQLiteDatabase db;
    SQLiteHelper helper;
    MediaPlayer mp;
    Date c = Calendar.getInstance().getTime();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modEquipo = findViewById(R.id.btnModEquipo);
        modPartidos = findViewById(R.id.btnModPartidos);
        activarSonido = findViewById(R.id.btnActivar);
        desactivarSonido = findViewById(R.id.btnDesactivar);
        helper = new SQLiteHelper(this);
        clasificacion = findViewById(R.id.btnClasificacion);
        db = helper.getWritableDatabase();
        mp = MediaPlayer.create(this, R.raw.elnano);
        db.delete("equipos", "_ID>0", null);
        db.delete(EstructuraBBDD.EstructuraPartidos.TABLE_NAME_PARTIDOS, "_ID>0", null);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String fecha = df.format(c);

       inserta("Real Madrid", "Madrid", 18, R.drawable.madrid);
       inserta("Atletico de Madrid", "Madrid", 18, R.drawable.atletico);
       inserta("FC Barcelona", "Barcelona", 2, R.drawable.barcelona);
       inserta("Sevilla FC", "Sevilla", 16, R.drawable.sevilla);
       inserta("Racing", "Santander", 19, R.drawable.racing);
       inserta("Betis", "Sevilla", 9, R.drawable.betis);
       inserta("Real Sociedad", "San Sebastián", 14, R.drawable.sociedad);

       insertar("Real Madrid", "FC Barcelona", "12", "14", fecha);

        Cursor cursor = db.query("equipos", null, null, null, null, null,null);
        Cursor cursor1 = db.query("partidos", null, null, null, null, null,null);

        modEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pasarModEquipo();

            }
        });

        modPartidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pasarModPartidos();
            }
        });

        clasificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pasarClasificacion();
            }
        });

        activarSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();
            }
        });

        desactivarSonido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.pause();
            }
        });
    }

    private void inserta(String nombre, String ciudad, int puntos,int foto) {
        ContentValues values= new ContentValues();
        values.put("nombre", nombre);
        values.put("ciudad", ciudad);
        values.put("puntos", puntos);
        values.put("foto",foto);
        db.insert("equipos",null, values);

    }

    public void insertar(String equipoLoc, String equipoVis, String puntosloc, String puntosVis, String fecha) {
        ContentValues values = new ContentValues();
        values.put("equipo1", equipoLoc);
        values.put("equipo2", equipoVis);
        values.put("puntuacion_local", puntosloc);
        values.put("puntuacion_visitante", puntosVis);
        values.put("fecha", fecha);
        db.insert("partidos", null, values);

    }

    public void pasarClasificacion() {
        Intent i = new Intent(this, Clasificacion.class);
        startActivity(i);
    }

    public void pasarModPartidos() {
        Intent i = new Intent(this, ModificarPartidos.class);
        startActivity(i);
    }

    public void pasarModEquipo() {
        Intent i = new Intent(this, ModificarEquipo.class);
        startActivity(i);
    }

}