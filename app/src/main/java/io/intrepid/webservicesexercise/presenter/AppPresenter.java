package io.intrepid.webservicesexercise.presenter;

import io.intrepid.webservicesexercise.model.GitHubUser;
import io.intrepid.webservicesexercise.network.GithubClient;
import io.intrepid.webservicesexercise.view.View;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by abassbayo-awoyemi on 1/11/17.
 */

public class AppPresenter implements Presenter {

    private static final String TAG =AppPresenter.class.getSimpleName();
    private static final String PLACE_HOLDER_URL = "https://avatars1.githubusercontent.com/u/7977903?v=3&s=400";
    private final View view;

    public AppPresenter(View view){
        this.view = view;
    }

    @Override
    public void onSearchEntered(String username) {
        Observable<GitHubUser> observable = GithubClient.getInstance().getUser(username);
        performRxWork(observable);
    }


    public void performRxWork(Observable<GitHubUser> observable){
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.e("Error rxJava retrofitting " + throwable);
                    }
                })
                .subscribe(new Action1<GitHubUser>() {
                    @Override
                    public void call(GitHubUser gitHubUser) {
                        Timber.d(TAG, "Rx java work");

                        String imageUrl = gitHubUser == null ? PLACE_HOLDER_URL : gitHubUser.getAvatarUrl();
                        view.showAvatar(imageUrl);
                    }
                });
    }

}
