package com.patterns.meena.mvprepopat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

/*
* Class Creation order
* MainActivity
* Contract
    Model-Injection happens here
    View
    Presenter
    */

public class MainActivity extends AppCompatActivity{

    public static final String GITHUB_FRAGMENT = "GITHUB_FRAGMENT";

   // private RepoPresenter mRepoPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mRepoPresenter = new RepoPresenter(this);
        attachGithubFragment();
        //repository access
    }

    private void attachGithubFragment() {
        GithubFragment githubFragment = new GithubFragment();

        Log.i("Knowing the flow", "attachGithubFragment() MainActivity");

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container_layout, githubFragment, GITHUB_FRAGMENT)
                .commit();
    }
}
