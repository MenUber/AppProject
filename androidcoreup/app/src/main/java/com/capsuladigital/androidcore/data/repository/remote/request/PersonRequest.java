package com.capsuladigital.androidcore.data.repository.remote.request;

import com.capsuladigital.androidcore.data.model.person.PersonEdit;
import com.capsuladigital.androidcore.data.model.person.PersonSignUpPost;
import com.capsuladigital.androidcore.data.model.team.TeamsEditPost;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface PersonRequest {

    //SignIn
    @POST("api/v1/person/register")
    Call<JsonObject> signUp(@Body PersonSignUpPost person);

    //Login
    @FormUrlEncoded
    @POST("api/v1/person/login")
    Call<JsonObject> login(@Field("personEmail") String email,
                           @Field("personPassword") String password,
                           @Field("language") String lan);

    //Verify Email
    @FormUrlEncoded
    @POST("api/v1/person/verifyemail")
    Call<JsonObject> verifyEmail(@Field("personEmail") String email);

    //EditPerson
    @PUT("api/v1/person/profile")
    Call<JsonObject> editPerson(@Header("Authorization") String token, @Body PersonEdit person);

    //editTeams
    @POST("api/v1/person/teams")
    Call<JsonObject> editTeams(@Header("Authorization") String token, @Body TeamsEditPost teams);

    //Send Password Change Attempt
    @FormUrlEncoded
    @POST("api/v1/person/sendcode")
    Call<JsonObject> sendPassUpdateAttempt(@Field("personEmail") String email);

    //Update Password
    @FormUrlEncoded
    @PUT("api/v1/person/updatepassword")
    Call<JsonObject> updatePassword(@Field("personEmail") String email,
                                    @Field("personPassword") String password,
                                    @Field("personUpdateCode") String code);

    //Social Media
    @FormUrlEncoded
    @POST("api/v1/person/socialmedia")
    Call<JsonObject> socialMedia(@Field("personEmail") String email,
                                 @Field("personName") String name,
                                 @Field("personLastName") String lastName,
                                 @Field("personImageUrl") String photo,
                                 @Field("personBirthDate") String birthday,
                                 @Field("language") String lan
    );

}
