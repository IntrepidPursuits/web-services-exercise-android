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

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {
    @BindView(R.id.username_input)
    EditText usernameInputView;
    @BindView(R.id.avatar)
    ImageView avatarView;

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());

        presenter = new MainActivityPresenter(this);
    }

    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        presenter.retrieveGithubUser(usernameInputView.getText().toString());
    }

    public void onUserRetrieved(GitHubUser gitHubUser) {
        Picasso.with(this)
                .load(gitHubUser.getAvatarUrl())
                .fit()
                .into(avatarView);
    }
}
