package com.example.hafleti.Network;

import com.example.hafleti.Models.RubriqueResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RubriqueApiService {
    @GET("api/rubriques/bySpectacle/{spectacleId}")
    Call<List<RubriqueResponse>> getRubriquesBySpectacle(@Path("spectacleId") Long spectacleId);
}
