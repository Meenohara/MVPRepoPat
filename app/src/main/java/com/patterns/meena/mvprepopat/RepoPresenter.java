package com.patterns.meena.mvprepopat;

import java.util.List;

/**
 * Responsible for handling actions from the View and updating the UI as required
 */
public class RepoPresenter implements RepoContract.Presenter {

    private RepoContract.View repoContractView;

    private RepoContract.Model repoContractModel;

    ///model or injection?
    //private RepoContract.Model repoContractModel;

    public RepoPresenter(RepoContract.View view)
    {
        this.repoContractView = view;//this will be used ofr findviewby id github_users_recyclerview
        this.repoContractModel = RepoInjection.provideRepoModel(this);
    }

    @Override
    public void getRepos(String userName) {

        repoContractModel.getReposFromGithub(userName);
    }

    @Override
    public void onGetReposSuccess(List<GithubRepo> repos) {
        repoContractView.showRepos(repos);
    }

    @Override
    public void onGetReposFailure(String errorMessage) {
        repoContractView.showError(errorMessage);
    }

    @Override
    public void showLoading() {
        repoContractView.showLoading();
    }

    @Override
    public void hideLoading() {
        repoContractView.hideLoading();
    }
}
