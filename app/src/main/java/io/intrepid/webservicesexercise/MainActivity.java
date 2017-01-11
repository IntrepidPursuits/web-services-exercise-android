package io.intrepid.webservicesexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.intrepid.webservicesexercise.model.GitHubUser;
import io.intrepid.webservicesexercise.network.GithubClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements Callback<GitHubUser> {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.username_input)
    EditText usernameInputView;
    @BindView(R.id.avatar)
    ImageView avatarView;
    private String placeHolderUrl = "https://avatars1.githubusercontent.com/u/7977903?v=3&s=400";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
    }

    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        if (usernameInputView.getText().length() > 0) {
            String usernme = usernameInputView.getText().toString();
            GithubClient.getInstance().search(usernme).enqueue(this);
        } else {
            Toast.makeText(this, "Nice try", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResponse(Call<GitHubUser> call, Response<GitHubUser> response) {
        String imageUrl = response.body() == null ? placeHolderUrl : response.body().getAvatarUrl();
        Picasso.with(this)
                .load(imageUrl)
                .fit()
                .into(avatarView);
    }

    @Override
    public void onFailure(Call<GitHubUser> call, Throwable t) {
        Log.d(TAG, "Failure retofitting " + t);
    }

}
