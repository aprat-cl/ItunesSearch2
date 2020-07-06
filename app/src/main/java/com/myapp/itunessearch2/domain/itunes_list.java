package com.myapp.itunessearch2.domain;

import android.content.Context;
import android.util.Log;

import com.myapp.itunessearch2.data.api.Responses.Itunes_SearchResponse;
import com.myapp.itunessearch2.data.api.itunes_service_adapter;
import com.myapp.itunessearch2.data.api.itunes_service_interface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Response;

public class itunes_list implements da_base{
    /**
     * Metodo que utiliza la api de itunes para la busqueda de canciones
     * @param par Parametro de tipo JSONObject con la cancion a buscar y el limite de busqueda.
     * @param cxt Contexto, en este caso no se usa, debe ser null
     * @return Objeto de tipo JSONArray con el resultado de la busqueda usando la api
     * @throws Exception
     */
    @Override
    public Object getData(Object par, Context cxt) throws Exception {
        String url = "https://itunes.apple.com";
        JSONObject parameters = (JSONObject)par;
        if(parameters.has("song") && parameters.has("limit")) {
            itunes_service_interface api_adapter = itunes_service_adapter.getApiInterface(url);
            try {
                Response<Itunes_SearchResponse> api_result = api_adapter.getSearch(parameters.getString("song"), "music", parameters.getString("limit")).execute();
                JSONArray resultado = new JSONArray();
                Itunes_SearchResponse songs = api_result.body();
                for (int i = 0; i < songs.results.size(); i++) {                    ;
                    JSONObject search_item = new JSONObject();
                    search_item.put("album", songs.results.get(i).collectionName);
                    search_item.put("banda", songs.results.get(i).artistName);
                    search_item.put("cancion", songs.results.get(i).trackName);
                    search_item.put("arte100", songs.results.get(i).artworkUrl100);

                    resultado.put(search_item);
                }
                return resultado;
            }
            catch(IOException ie){
                Log.e("Error Searching", ie.getMessage());
                return new JSONArray();
            }
        }
        return new JSONArray();
    }

    /**
     * **Metodo no utilizado**
     * @param obj
     * @param cxt
     * @return
     */
    @Override
    public boolean setData(Object obj, Context cxt) {
        return false;
    }

}
