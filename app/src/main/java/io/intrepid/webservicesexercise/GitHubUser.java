package io.intrepid.webservicesexercise;

import com.google.gson.annotations.SerializedName;

class GitHubUser {
    @SerializedName("avatar_url")
    private String avatarUrl;

    String getAvatarUrl() {
        return avatarUrl;
    }
}
