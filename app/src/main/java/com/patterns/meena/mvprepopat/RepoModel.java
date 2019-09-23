package com.patterns.meena.mvprepopat;

import android.util.Log;
import android.widget.Toast;

import com.patterns.meena.mvprepopat.RepoPresenter;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepoModel implements RepoContract.Model {


    private static final String TAG = "ORDER";

    private static final String NO_INTERNET_MESSAGE = "No internet connection.";
    private static final String REMOTE_SERVER_FAILED_MESSAGE = "Application server could not respond.";
    private static final String UNEXPECTED_ERROR_OCCURRED = "Something went wrong.";


    private RepoPresenter repoPresenter;

    public RepoModel(RepoPresenter repoPresenter) {
        this.repoPresenter = repoPresenter;
    }

    @Override
    public void getReposFromGithub(String userName) {
        // you make github call
        repoPresenter.showLoading();
        GithubService retrofitApiService = GithubInjection.provideGithubService();
          retrofitApiService.getMyRepos(userName).enqueue((new Callback<List<GithubRepo>>() {
         //* new Callback() {
        @Override
        public void onResponse(Call<List<GithubRepo>> call, Response<List<GithubRepo>> response) {
        // Presenter will act like a proxy. Just calling similar methods.
            repoPresenter.hideLoading();
            if (response.isSuccessful()) { // if code between 200 and 300
                repoPresenter.onGetReposSuccess(response.body());
            } else {
                // error case
                switch (response.code()) {
                    case 404:
                        repoPresenter.onGetReposFailure("Username not found");
                        break;
                    case 500:
                        repoPresenter.onGetReposFailure("Server broken");
                        break;
                    default:
                        repoPresenter.onGetReposFailure("Unknown Error");

                        break;
                }
            }
            Log.i(TAG, "onResponse");
           }


           public void onFailure(Call<List<GithubRepo>> call, Throwable throwable) {
               Log.i(TAG, "onFailure");
               repoPresenter.hideLoading();

               String exception = resolveException(throwable);
               repoPresenter.onGetReposFailure(exception);

           }
          }
          ));

    }

    String resolveException(Throwable exception) {//Changing this also to Exception gave an error so has been
        //retained as Throwable
        if (exception instanceof UnknownHostException) {
            return NO_INTERNET_MESSAGE;
        } else if (exception instanceof SocketTimeoutException) {
            return REMOTE_SERVER_FAILED_MESSAGE;
        } else if (exception instanceof ConnectException) {
            return NO_INTERNET_MESSAGE;
        } else {
            return UNEXPECTED_ERROR_OCCURRED;
        }
    }
}


//TODO
//is this where repository class is also implemented?
//what is my repository here? api.github.com
//What to "inject" here?
//In GitHubRepo project data is not persistent
//A persistence layer needs to be added
//where does DAO fit in here?