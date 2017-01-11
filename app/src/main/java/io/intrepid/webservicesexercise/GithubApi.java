package io.intrepid.webservicesexercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{user}")
    Call<List<GitHubUser>> listUsers(@Path("user") String user);
}
