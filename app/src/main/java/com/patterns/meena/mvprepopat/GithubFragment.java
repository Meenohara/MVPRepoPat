package com.patterns.meena.mvprepopat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */

public class GithubFragment extends Fragment implements RepoContract.View {

    private static final String TAG = "ORDER";

    private RecyclerView githubRepoRecyclerView;
    private GitHubRepoAdapter adapter;
    private RepoContract.Presenter mRepoPresenter;


    ProgressBar loadingProgressBar;
    EditText userNameEditText;
    Button submitButton;


    public GithubFragment() {
        // Required empty public constructor

        Log.i(TAG, "GithubFragment Constructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "GithubFragment OnCreateView");
        return inflater.inflate(R.layout.fragment_github, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "GithubFragment onViewCreated");

        userNameEditText = view.findViewById(R.id.searchbox);
        submitButton = view.findViewById(R.id.submit);

        loadingProgressBar = view.findViewById(R.id.progressBar);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String query = userNameEditText.getText().toString();
                mRepoPresenter.getRepos(query);
                ViewUtil.hideKeyboard(userNameEditText);
            }
        });

        mRepoPresenter = RepoInjection.provideRepoPresenter(this);
        // setup RecyclerView
        ///where should this be? Presenter?
        githubRepoRecyclerView = view.findViewById(R.id.github_users_recyclerview);
        githubRepoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GitHubRepoAdapter(new ArrayList<GithubRepo>());
        githubRepoRecyclerView.setAdapter(adapter);
        Toast.makeText(getActivity(), "GithubFragment", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRepos(List<GithubRepo> repos) {
        Log.i(TAG, "GithubFragment showRepos");
        adapter.updateGithubRepo(repos);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String errorMessage) {
        Log.i(TAG, "GithubFragment showError");
        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoading() {
         loadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        loadingProgressBar.setVisibility(View.GONE);
    }


}
//is this meant to be the View?