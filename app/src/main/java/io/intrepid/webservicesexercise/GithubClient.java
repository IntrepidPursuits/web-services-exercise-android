package io.intrepid.webservicesexercise;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubClient {
    public static final String BASE_URL = "https://api.github.com/";
    private static final long CONNECTION_TIMEOUT = 15;

    private static GithubApi instance;

    public static GithubApi getInstance() {
        if (instance == null) {
            instance = createRestApi();
        }
        return instance;
    }

    private static GithubApi createRestApi() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient)
                .addConverterFactory(getConverter())
                .build();

        return retrofit.create(GithubApi.class);
    }

    private static Converter.Factory getConverter() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        return GsonConverterFactory.create(gson);
    }
}
