package com.example.hafleti.Network;

import com.example.hafleti.Models.ClientDTO;
import com.example.hafleti.Models.LoginRequest;
import com.example.hafleti.Models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/api/clients/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/api/clients/clientByEmail/{email}")
    Call<ClientDTO> getClientByEmail(@Path("email") String email);

    @PUT("/api/clients")
    Call<ClientDTO> updateClient(@Body ClientDTO clientDTO);

}
