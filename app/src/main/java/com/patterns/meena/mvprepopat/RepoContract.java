package com.patterns.meena.mvprepopat;

import java.util.List;

public interface RepoContract {


    interface Model {
        //have inject here?
        void getReposFromGithub(String userName);
    }

    interface View {

        void showRepos(List<GithubRepo> repos);

        void showError(String errorMessage);

        void showLoading();

        void hideLoading();
        //displaying the repos
    }

    interface Presenter {
        //forwarding the display request to Model

        void getRepos(String userName);

        void onGetReposSuccess(List<GithubRepo> repos);

        void onGetReposFailure(String errorMessage);

        void showLoading();

        void hideLoading();
    }
}
