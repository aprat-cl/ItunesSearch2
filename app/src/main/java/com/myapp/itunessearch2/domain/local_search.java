package com.myapp.itunessearch2.domain;

import android.content.Context;
import android.util.Log;

import com.myapp.itunessearch2.data.db.DatabaseMan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class local_search implements da_base {
    @Override
    public Object getData(Object parameters, Context cxt) throws Exception {
        DatabaseMan db = new DatabaseMan(cxt);
        return db.getHistoricSearch(parameters.toString());
    }

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
