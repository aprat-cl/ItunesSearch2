package com.myapp.itunessearch2.data.api;

import okhttp3.OkHttpClient;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class itunes_service_adapter {
    private static String _BASE_URL = "";
    private static itunes_service_interface API_INTERFACE;
    public static itunes_service_interface getApiInterface(String BaseUrl){
        //if(_BASE_URL != BaseUrl && API_INTERFACE != null)
        API_INTERFACE = null;
        _BASE_URL = BaseUrl;

        HttpLoggingInterceptor log = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient.Builder client = new OkHttpClient.Builder().addInterceptor(log);

        if(API_INTERFACE == null){
            Retrofit rfit = new Retrofit.Builder()
                    .baseUrl(_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();
            API_INTERFACE = rfit.create(itunes_service_interface.class);
            return API_INTERFACE;
        }
        else
            return null;
    };
}
