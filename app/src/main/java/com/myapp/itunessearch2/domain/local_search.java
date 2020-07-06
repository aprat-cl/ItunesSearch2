package com.myapp.itunessearch2.domain;

import android.content.Context;
import android.util.Log;

import com.myapp.itunessearch2.data.db.DatabaseMan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class local_search implements da_base {
    /**
     * Metodo que obtiene la busqueda historica almacenada previamente
     * @param parameters String que contiene la busqueda para obtener el historico
     * @param cxt Context de la aplicacion para acceder a la base de datos interna
     * @return String con el resultado de la busqueda realizada previamente
     * @throws Exception
     */
    @Override
    public Object getData(Object parameters, Context cxt) throws Exception {
        DatabaseMan db = new DatabaseMan(cxt);
        return db.getHistoricSearch(parameters.toString());
    }

    /**
     * Metodo que guarda la busqueda realizada con exito en linea
     * @param obj JSONObject con la busqueda y su resultado para almacenarla en la BD Historica
     * @param cxt Context de la aplicacion para acceder a la bd interna
     * @return verdadero si se pudo guardar la informacion, falso si hubo algun problema
     */
    @Override
    public boolean setData(Object obj, Context cxt) {
        DatabaseMan db = new DatabaseMan(cxt);
        try {
            JSONObject jo_params = (JSONObject) obj;
            db.setHistoricSearch(jo_params.getString("Search"), jo_params.getString("Result").replace("'", "`"));
            return true;
        }
        catch(Exception ex){
            Log.e("LocalHistoricError", ex.getMessage());
            return false;
        }
    }
}
