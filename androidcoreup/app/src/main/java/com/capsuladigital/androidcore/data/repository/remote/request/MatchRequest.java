package com.capsuladigital.androidcore.data.repository.remote.request;

import com.capsuladigital.androidcore.data.model.match.MatchesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MatchRequest {

    //getLeagueMatches
    @GET("api/v1/tournaments/{id}/matches/{lan}")
    Call<MatchesResponse> getMatches(@Path("id") int id, @Path("lan") String lan);

}