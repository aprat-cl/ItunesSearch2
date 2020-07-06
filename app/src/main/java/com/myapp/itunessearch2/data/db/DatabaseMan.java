package com.myapp.itunessearch2.data.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseMan extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";
    public static final String _SAVE_TABLE = "Offline_Search";
    public DatabaseMan(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sQuery = "CREATE TABLE " + _SAVE_TABLE + " (" +
                "Search TEXT PRIMARY KEY, " +
                "Result TEXT)";
        db.execSQL(sQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + _SAVE_TABLE );
    }

    /**
     * Obtiene desde la base de datos la busqueda historica segun su llave
     * @param Query String llave de busqueda historica
     * @return String con la busqueda historica o vacia si no existe
     * @throws Exception
     */
    public String getHistoricSearch(String Query) throws Exception
    {
        List<String> result = new ArrayList<>();
        String sQuery = "SELECT Result FROM " + _SAVE_TABLE + " WHERE Search = '" + Query + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sQuery, null);

        while (cursor.moveToNext()) {
            return cursor.getString(0);
        }

        return "";
    }

    /**
     * Metodo que establece la busqueda historica por su llave
     * @param Search String con la llave de busqueda para la tabla
     * @param Result String con el resultado de la busqueda para almacenar
     * @throws Exception
     */
    public void setHistoricSearch(String Search, String Result) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT OR REPLACE INTO " + _SAVE_TABLE + " VALUES ('" + Search + "', '" + Result + "')");
    }
}