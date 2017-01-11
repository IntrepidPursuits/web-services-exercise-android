package io.intrepid.webservicesexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private static String GITHUB_BASE_URL = "https://api.github.com/";
    @BindView(R.id.username_input)
    EditText usernameInputView;
    @BindView(R.id.avatar)
    ImageView avatarView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());

    }

    private static GitHubApi createRestApi() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        OkHttpClient httpClient = builder
                .connectTimeout(2, TimeUnit.SECONDS)
                .build();
        return new Retrofit.Builder()
                .baseUrl(GITHUB_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubApi.class);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        Call<GitHubUser> call = createRestApi().getUser(usernameInputView.getText().toString());
        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
                GitHubUser user = response.body();
                Picasso.with(getApplicationContext())
                        .load(user.getAvatarUrl())
                        .fit()
                        .into(avatarView);
            }

            @Override
            public void onFailure(Call<GitHubUser> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
