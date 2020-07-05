package com.myapp.itunessearch2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.myapp.itunessearch2.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ItunesSearchAdapter extends ArrayAdapter<JSONObject> {
    public ItunesSearchAdapter(@NonNull Context context, List<JSONObject> data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        JSONObject data = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_search_list, parent, false);
        try {
            String cancion = data.getString("cancion");
            String album = data.getString("album");
            String banda = data.getString("banda");

            TextView tv_cancion = convertView.findViewById(R.id.item_cancion);
            TextView tv_banda = convertView.findViewById(R.id.item_banda);
            TextView tv_album = convertView.findViewById(R.id.item_album);
            ImageView iv_arte = convertView.findViewById(R.id.item_arte);

            tv_cancion.setText(cancion);
            tv_album.setText("Album: " + album);
            tv_banda.setText("Banda: " + banda);

        }
        catch(Exception ex){
            Log.e("ErrorParsinSong", ex.getMessage());
        }
        return convertView;
    }
}
