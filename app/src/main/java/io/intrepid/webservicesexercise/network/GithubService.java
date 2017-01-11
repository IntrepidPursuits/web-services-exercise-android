package io.intrepid.webservicesexercise.network;

import io.intrepid.webservicesexercise.model.GitHubUser;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by abassbayo-awoyemi on 1/11/17.
 */

public interface GithubService {

    @GET("/users/{username}")
    Call<GitHubUser> getUser(@Path("username") String username);

}
