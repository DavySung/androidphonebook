package com.example.ast2_phonebook.Helper;

import com.example.ast2_phonebook.Model.PhonebookModel;
import com.example.ast2_phonebook.Room.Entities.Phonebook;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RemotePhonebookDB {

    @POST("/api/Phonebooks")
    Call<PhonebookModel> PhonebookCreate(@Body PhonebookModel phonebook);

    @GET("/api/Phonebooks")
    Call<List<PhonebookModel>> PhonebookAll();

    @GET("api/Phonebooks/{id}")
    Call<PhonebookModel> PhonebookGet(@Path("id") int id);

    @PUT("/api/Phonebooks/{id}")
    Call<Void> PhonebookUpdate(@Path("id") int id, @Body PhonebookModel phonebook);

    @DELETE("/api/Phonebooks/{id}")
    Call<PhonebookModel> PhonebookDelete(@Path("id") int id);


}
