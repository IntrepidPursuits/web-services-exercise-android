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
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

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
            String username = usernameInputView.getText().toString();
            Observable<GitHubUser> observable = GithubClient.getInstance().search(username);
            performRxWork(observable);
        } else {
            Toast.makeText(this, "Nice try", Toast.LENGTH_SHORT).show();
        }

    }

    public void performRxWork(Observable<GitHubUser> observable){
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GitHubUser>() {
                    @Override
                    public void call(GitHubUser gitHubUser) {
                        Log.d(TAG, "Rx java work");
                        String imageUrl = gitHubUser == null ? placeHolderUrl : gitHubUser.getAvatarUrl();
                        Picasso.with(MainActivity.this)
                                .load(imageUrl)
                                .fit()
                                .into(avatarView);
                    }
                });
    }

}
