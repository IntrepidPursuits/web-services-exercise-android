package io.intrepid.webservicesexercise;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivityPresenter {
    interface View {
        void onUserRetrieved(GitHubUser gitHubUser);
    }

    private final View view;

    MainActivityPresenter(View view) {
        this.view = view;
    }

    public GitHubUser retrieveGithubUser(String username) {
        Call<GitHubUser> call = GithubClient.getInstance()
                .getUser(username);
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                if (response.isSuccessful()) {
                    GitHubUser user = response.body();
                    view.onUserRetrieved(user);
                } else {
                    Timber.d("Error", response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                Timber.d("Error", t.getMessage());
            }
        });
        return null;
    }
}
