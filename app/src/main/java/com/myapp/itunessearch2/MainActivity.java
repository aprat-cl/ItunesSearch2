package com.myapp.itunessearch2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import com.myapp.itunessearch2.adapters.ItunesSearchAdapter;
import com.myapp.itunessearch2.domain.itunes_list;
import com.myapp.itunessearch2.domain.local_search;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    LinearLayout listWait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onResume(){
        super.onResume();

        SearchView search = findViewById(R.id.search_bar);
        listView = findViewById(R.id.search_result);
        listWait = findViewById(R.id.wait);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.equals("")) return false;
                try {
                    AsyncTaskRunner task = new AsyncTaskRunner();
                    task.execute(query);
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
    private class AsyncTaskRunner extends AsyncTask<String, String, JSONArray> {

        private String resp;

        @Override
        protected JSONArray doInBackground(String... params) {
            //Se inicia el proceso de búsqueda en segundo plano y se almacena su resultado
            publishProgress("Realizando Busqueda"); // Calls onProgressUpdate()
            itunes_list il = new itunes_list();
            JSONObject pars = new JSONObject();
            JSONArray resp = new JSONArray();
            try {
                pars.put("song", params[0]);
                pars.put("limit", 20);
                resp = (JSONArray)il.getData(pars, null);
                local_search ls = new local_search();
                if(resp.length() == 0){
                    String temp_result = (String)ls.getData(params[0], getApplicationContext());
                    resp = new JSONArray(temp_result);
                }
                else{
                    String temp_result = resp.toString();
                    JSONObject jo_result = new JSONObject();
                    jo_result.put("Search", params[0]);
                    jo_result.put("Result", temp_result);
                    ls.setData(jo_result, getApplicationContext());
                }
            }
            catch(Exception ex){
                Log.e("ErrorDownloading", ex.getMessage());
            }

            return resp;
        }


        @Override
        protected void onPostExecute(JSONArray resp) {
            //Al terminar el proceso de busqueda se llama a el adapter para llenar los datos
            List<JSONObject> arResult = new ArrayList<>();
            try {
                arResult = new ArrayList<>();
                for (int i = 0; i < resp.length(); i++) {
                    arResult.add(resp.getJSONObject(i));
                }
            }
            catch(Exception ex){
                Log.e("ErrorParsing", ex.getMessage());
            }
            ItunesSearchAdapter adapter = new ItunesSearchAdapter(MainActivity.this, arResult);

            ListView listView = findViewById(R.id.search_result);
            listView.setAdapter(adapter);
            listWait.setVisibility(LinearLayout.GONE);
        }


        @Override
        protected void onPreExecute() {
            //Antes de ejecutar la operacion ens egundo plano mostramos el Progress dialog en el layout
            listWait.setVisibility(LinearLayout.VISIBLE);
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //Aca llenar el procentaje de busqueda...

        }
    }
}