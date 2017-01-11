package io.intrepid.webservicesexercise.network;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.intrepid.webservicesexercise.model.GitHubUser;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by abassbayo-awoyemi on 1/11/17.
 */

public class GithubClient {

    private static final long CONNECTION_TIMEOUT = 30;
    private static final String BASE_URL = "https://api.github.com/users/";

    private static GithubClient instance;
    private GithubService service;

    public static GithubClient getInstance(){
        if(instance == null) {
            instance = new GithubClient();
        }
        return instance;
    }

    private GithubClient() {
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
    }

    public Observable<GitHubUser> search(String username){
        return service.getUser(username);
    }
}
