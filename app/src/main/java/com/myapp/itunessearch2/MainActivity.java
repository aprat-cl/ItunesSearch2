package com.myapp.itunessearch2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SearchView;

import com.myapp.itunessearch2.adapters.ItunesSearchAdapter;
import com.myapp.itunessearch2.domain.itunes_list;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onResume(){
        super.onResume();

        SearchView search = findViewById(R.id.search_bar);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")) return false;
                itunes_list il = new itunes_list();
                JSONObject params = new JSONObject();
                try {
                    params.put("song", query);
                    params.put("limit", 20);
                    JSONArray resp = il.getData(params);
                    if(resp == null) return false;

                    List<JSONObject> arResult = new ArrayList<>();
                    for(int i = 0; i < resp.length(); i++){
                        arResult.add(resp.getJSONObject(i));
                    }
                    ItunesSearchAdapter adapter = new ItunesSearchAdapter(MainActivity.this, arResult);

                    ListView listView = findViewById(R.id.search_result);
                    listView.setAdapter(adapter);
                }
                catch (Exception ex){
                    Log.e("JsonError", ex.getMessage());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }
}