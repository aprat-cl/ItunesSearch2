package com.myapp.itunessearch2.domain;
import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface da_base {
    public JSONArray getData(JSONObject parameters) throws JSONException;
    public boolean setData(JSONObject obj);
}
