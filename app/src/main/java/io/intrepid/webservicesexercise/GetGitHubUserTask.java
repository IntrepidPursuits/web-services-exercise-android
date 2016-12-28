package io.intrepid.webservicesexercise;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import timber.log.Timber;

class GetGitHubUserTask extends AsyncTask<String, Void, GitHubUser> {
    private Callback callback;

    @Override
    protected GitHubUser doInBackground(String... params) {
        try {
            URL url = new URL("https://api.github.com/users/" + params[0]);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            httpURLConnection.disconnect();

            Gson gson = new Gson();
            return gson.fromJson(response.toString(), GitHubUser.class);
        } catch (IOException e) {
            Timber.e(e);
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
