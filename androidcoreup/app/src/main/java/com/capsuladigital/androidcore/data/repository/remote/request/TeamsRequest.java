package com.capsuladigital.androidcore.data.repository.remote.request;

import com.capsuladigital.androidcore.data.model.team.SquadResponse;
import com.capsuladigital.androidcore.data.model.team.TeamsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface TeamsRequest {
    
    //getAllTeams
    @GET("api/v1/teams/{lang}")
    Call<TeamsResponse> getAllTeams(@Path("lang") String lan);

    //getTeamSquad
    @GET("api/v1/teams/{id}/squad")
    Call<SquadResponse> getTeamSquad(@Path("id") int id);


}
