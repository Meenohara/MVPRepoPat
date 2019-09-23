package com.patterns.meena.mvprepopat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

  /*  @GET("users?since=135")
    Call<List<GithubUser>> getGithubUsers();*/

    @GET("users/{username}/repos")
    Call<List<GithubRepo>> getMyRepos(@Path("username") String username);
}
