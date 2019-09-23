package com.patterns.meena.mvprepopat;

import android.util.Log;

final class RepoInjection {

    private RepoInjection() {
        Log.i("ORDER", "In RepoInjection() constructor, RepoInjection");
        throw new AssertionError("Injection object is not allowed");
    }

    static RepoContract.Presenter provideRepoPresenter(
            RepoContract.View view
    ) {
        Log.i("ORDER", "In provideRepoPresenter(), RepoInjection");
        return new RepoPresenter(view);
    }

    static RepoContract.Model provideRepoModel(
            RepoPresenter repoPresenter
    ) {
        Log.i("ORDER", "In provideRepoModel(), RepoInjection");
        return new RepoModel(repoPresenter);
    }
}
