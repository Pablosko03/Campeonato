package com.example.campeonato;

import android.provider.BaseColumns;

public class EstructuraBBDD {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraEquipo.TABLE_NAME_EQUIPOS
                    +
                    "(" + EstructuraEquipo._ID + " integer PRIMARY KEY, "
                    + EstructuraEquipo.COLUMN_NAME_NOMBRE+ " text, "
                    + EstructuraEquipo.COLUMN_NAME_CIUDAD + " text, "
                    + EstructuraEquipo.COLUMN_PUNTOS_ACUMULADOS + "text, "
                    + EstructuraEquipo.COLUMN_FOTO + " integer);";
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + EstructuraEquipo.TABLE_NAME_EQUIPOS;

    public static final String SQL_CREATE_ENTRIES1 =
            "CREATE TABLE IF NOT EXISTS "+ EstructuraPartidos.TABLE_NAME_PARTIDOS
                    +
                    "(" + EstructuraPartidos._ID + " integer PRIMARY KEY, "
                    + EstructuraPartidos.COLUMN_NAME_EQUIPO1+ " text, "
                    + EstructuraPartidos.COLUMN_NAME_EQUIPO2 + " text, "
                    + EstructuraPartidos.COLUMN_NAME_PUNTUACION_LOCAL + "text, "
                    + EstructuraPartidos.COLUMN_NAME_PUNTUACION_VISITANTE + " text, "
                    + EstructuraPartidos.COLUMN_NAME_FECHA + "text);";
    public static final String SQL_DELETE_ENTRIES1 =
            "DROP TABLE IF EXISTS " + EstructuraPartidos.TABLE_NAME_PARTIDOS;

    private EstructuraBBDD() {}
    /* Clase interna que define la estructura de la tabla de equipos */
    public static class EstructuraEquipo implements BaseColumns {
        public static final String TABLE_NAME_EQUIPOS = "equipos";
        public static final String COLUMN_NAME_NOMBRE = "nombre";
        public static final String COLUMN_NAME_CIUDAD = "ciudad";
        public static final String COLUMN_PUNTOS_ACUMULADOS = "puntos";
        public static final String COLUMN_FOTO = "foto";
    }

    public static class EstructuraPartidos implements BaseColumns {
        public static final String TABLE_NAME_PARTIDOS = "partidos";
        public static final String COLUMN_NAME_EQUIPO1 = "equipo1";
        public static final String COLUMN_NAME_EQUIPO2 = "equipo2";
        public static final String COLUMN_NAME_PUNTUACION_LOCAL = "puntuacion_local";
        public static final String COLUMN_NAME_PUNTUACION_VISITANTE = "puntuacion_visitante";
        public static final String COLUMN_NAME_FECHA = "fecha";
    }
}