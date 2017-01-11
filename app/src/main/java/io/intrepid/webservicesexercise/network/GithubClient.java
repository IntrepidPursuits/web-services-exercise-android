package io.intrepid.webservicesexercise.network;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by abassbayo-awoyemi on 1/11/17.
 */

public class GithubClient {

    private static final long CONNECTION_TIMEOUT = 30;
    private static final String BASE_URL = "https://api.github.com";

    private static GithubService service;

    public static GithubService getInstance(){
        if (service == null) {
            Gson gson = new Gson();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            service = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client)
                    .build()
                    .create(GithubService.class);
        }
        return service;
    }

}
