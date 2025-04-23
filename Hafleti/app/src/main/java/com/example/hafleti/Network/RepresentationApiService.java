package com.example.hafleti.Network;

import com.example.hafleti.Models.RepresentationResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RepresentationApiService {

    @GET("api/representations/spectacle/{spectacleId}")
    Call<List<RepresentationResponseDTO>> getBySpectacle(@Path("spectacleId") Long spectacleId);
}
