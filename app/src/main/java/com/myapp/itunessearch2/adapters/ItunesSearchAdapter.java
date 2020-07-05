package com.myapp.itunessearch2.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.util.List;

public class ItunesSearchAdapter extends ArrayAdapter<JSONObject> {
    public ItunesSearchAdapter(@NonNull Context context, List<JSONObject> data) {
        super(context, 0, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        JSONObject data = getItem(position);
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_custom_search_list, parent, false);
        try {
            String cancion = data.getString("cancion");
            String album = data.getString("album");
            String banda = data.getString("banda");

            TextView tv_cancion = convertView.findViewById(R.id.item_cancion);
            TextView tv_banda = convertView.findViewById(R.id.item_banda);
            TextView tv_album = convertView.findViewById(R.id.item_album);
            ImageView iv_arte = convertView.findViewById(R.id.item_arte);

            DownloadImageTask dit = new DownloadImageTask(iv_arte);
            dit.execute(data.getString("arte100"));

            tv_cancion.setText(cancion);
            tv_album.setText("Album: " + album);
            tv_banda.setText("Banda: " + banda);

        }
        catch(Exception ex){
            Log.e("ErrorParsinSong", ex.getMessage());
        }
        return convertView;
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

