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
    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Realizando Busqueda"); // Calls onProgressUpdate()
            itunes_list il = new itunes_list();
            JSONObject pars = new JSONObject();
            JSONArray resp = new JSONArray();
            try {
                pars.put("song", params[0]);
                pars.put("limit", 20);
                resp = il.getData(pars);

            }
            catch(Exception ex){
                Log.e("ErrorDownloading", ex.getMessage());
            }

            return resp.toString();
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            List<JSONObject> arResult = new ArrayList<>();
            try {
                JSONArray resp = new JSONArray(result);
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
            listWait.setVisibility(LinearLayout.VISIBLE);
        }


        @Override
        protected void onProgressUpdate(String... text) {
            //Aca llenar el procentaje de busqueda...

        }
    }
}