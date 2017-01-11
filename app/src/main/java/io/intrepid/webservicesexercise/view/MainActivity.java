package io.intrepid.webservicesexercise.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.intrepid.webservicesexercise.R;
import io.intrepid.webservicesexercise.presenter.AppPresenter;
import io.intrepid.webservicesexercise.presenter.Presenter;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements View {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.username_input)
    EditText usernameInputView;
    @BindView(R.id.avatar)
    ImageView avatarView;
    private Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new AppPresenter(this);
        ButterKnife.bind(this);
        Timber.plant(new Timber.DebugTree());
    }

    @OnClick(R.id.submit_button)
    public void submitButtonClicked() {
        if (usernameInputView.getText().length() > 0) {
            String username = usernameInputView.getText().toString();
            acceptSearch(username);
        } else {
            Toast.makeText(this, "Nice try", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void acceptSearch(String username) {
        presenter.onSearchEntered(username);
    }

    @Override
    public void showAvatar(String avatarUrl) {
        if(avatarUrl != null) {
            Picasso.with(MainActivity.this)
                    .load(avatarUrl)
                    .fit()
                    .into(avatarView);
        }
    }
}
