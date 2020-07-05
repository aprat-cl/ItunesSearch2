package com.myapp.itunessearch2.domain;

import android.util.Log;

import com.myapp.itunessearch2.data.Responses.Itunes_SearchResponse;
import com.myapp.itunessearch2.data.itunes_service_adapter;
import com.myapp.itunessearch2.data.itunes_service_interface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class itunes_list implements da_base{
    @Override
    public JSONArray getData(JSONObject parameters) throws JSONException {
        String url = "https://itunes.apple.com";
        if(parameters.has("song") && parameters.has("limit")) {
            itunes_service_interface api_adapter = itunes_service_adapter.getApiInterface(url);
            try {
                Response<List<Itunes_SearchResponse>> songs = api_adapter.getSearch(parameters.getString("songs"), "music", parameters.getString("limit")).execute();
                JSONArray resultado = new JSONArray();

                for (Itunes_SearchResponse item: songs.body()) {
                    JSONObject search_item = new JSONObject();
                    search_item.put("album", item.collectionName);
                    search_item.put("banda", item.artistName);
                    search_item.put("cancion", item.trackName);
                    search_item.put("arte100", item.artworkUrl100);

                    resultado.put(search_item);
                }
                return resultado;
            }
            catch(IOException ie){
                Log.e("Error Searching", ie.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean setData(JSONObject obj) {
        return false;
    }

}
