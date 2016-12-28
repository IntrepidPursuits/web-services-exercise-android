package io.intrepid.webservicesexercise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements GetGitHubUserTask.Callback {
    @BindView(R.id.username_input)
    EditText usernameInputView;
    @BindView(R.id.avatar)
    ImageView avatarView;

    private GetGitHubUserTask getGitHubUserTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (getGitHubUserTask != null) {
            getGitHubUserTask.setCallback(null);
        }
    }

    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        getGitHubUserTask = new GetGitHubUserTask();
        getGitHubUserTask.setCallback(this);
        getGitHubUserTask.execute(usernameInputView.getText().toString());
    }

    @Override
    public void onResult(GitHubUser gitHubUser) {
        Picasso.with(this)
                .load(gitHubUser.getAvatarUrl())
                .fit()
                .into(avatarView);
    }
}
