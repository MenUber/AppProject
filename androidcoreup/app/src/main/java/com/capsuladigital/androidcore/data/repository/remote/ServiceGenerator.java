package com.capsuladigital.androidcore.data.repository.remote;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    public static final String API_BASE_URL = WS.root;

    private static OkHttpClient httpClient = new OkHttpClient();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    public static final String API_SOCCER_URL = WS.soccer;

    private static Retrofit.Builder builderSoccer =
            new Retrofit.Builder()
                    .baseUrl(API_SOCCER_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createServiceSoccerAPI(Class<S> serviceClass) {
        Retrofit retrofit = builderSoccer.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

}


