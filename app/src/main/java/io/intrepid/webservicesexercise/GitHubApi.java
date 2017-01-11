package io.intrepid.webservicesexercise;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by matttompson on 1/11/17.
 */

public interface GitHubApi {

    @GET("users/{user}")
    Call<GitHubUser> getUser(@Path("user") String user);
}
