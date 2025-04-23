package com.example.hafleti.Network;

import com.example.hafleti.Models.Spectacle;
import com.example.hafleti.Models.SpectacleHomeDTO;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpectacleApiService {
    @GET("/api/spectacles") // Endpoint exact comme dans votre controller Spring
    Call<List<Spectacle>> getAllSpectacles();
    @GET("/api/spectacles/{id}")
    Call<SpectacleHomeDTO> getById(@Path("id") Long id);

}