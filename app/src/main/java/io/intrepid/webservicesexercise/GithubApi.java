package io.intrepid.webservicesexercise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {
    @GET("users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);
}
