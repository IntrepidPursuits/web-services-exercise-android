package io.intrepid.webservicesexercise;

import android.os.AsyncTask;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class GetGitHubUserTask extends AsyncTask<String, Void, GitHubUser> {
    public static final String BASE_URL = "https://api.github.com/";
    private static final long CONNECTION_TIMEOUT = 15;

    private Callback callback;

    private static GithubApi createRestApi() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient httpClient = builder
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

    @Override
    protected GitHubUser doInBackground(String... params) {

        try {
            GithubApi api = createRestApi();

            Call<GitHubUser> call = api.getUser(params[0]);
            GitHubUser user = call.execute().body();

            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(GitHubUser gitHubUser) {
        super.onPostExecute(gitHubUser);

        if (callback != null) {
            callback.onResult(gitHubUser);
        }
    }

    void setCallback(Callback callback) {
        this.callback = callback;
    }

    interface Callback {
        void onResult(GitHubUser gitHubUser);
    }
}
