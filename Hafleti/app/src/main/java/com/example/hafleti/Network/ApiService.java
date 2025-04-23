package com.example.hafleti.Network;

import com.example.hafleti.Models.Client;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("/api/clients/signup")
    Call<Map<String, String>> registerClient(@Body Client client);}
