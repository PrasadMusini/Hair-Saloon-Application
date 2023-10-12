package com.example.practice.searchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API_Calls {

//    http://localhost:3000/users

// Define the relative URL for the endpoint you want to access
    @GET("/jessy")
    Call<List<User>> getUsers();

}