package com.myapp.itunessearch2.domain;
import android.content.Context;

import com.google.gson.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface da_base {
    public Object getData(Object pars, Context cxt) throws Exception;
    public boolean setData(Object obj, Context cxt);
}
