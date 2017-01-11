package io.intrepid.webservicesexercise.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {

    @SerializedName("avatar_url")
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
