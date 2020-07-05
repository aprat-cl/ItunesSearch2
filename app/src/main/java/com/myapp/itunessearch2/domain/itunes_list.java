package com.myapp.itunessearch2.domain;

import android.util.Log;

import com.myapp.itunessearch2.data.Responses.Itunes_SearchResponse;
import com.myapp.itunessearch2.data.Responses.Itunes_SearchResponse_item;
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

    @Override
    public boolean setData(JSONObject obj) {
        return false;
    }

}
