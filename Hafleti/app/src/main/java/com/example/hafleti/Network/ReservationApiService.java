// ReservationApiService.java
package com.example.hafleti.Network;

import com.example.hafleti.Models.BilletPurchaseRequest;
import com.example.hafleti.Models.Reservation;
import com.example.hafleti.Models.ReservationDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReservationApiService {
    @POST("api/reservations/client/{clientId}")
    Call<Reservation> createReservationForClient(
            @Path("clientId") Long clientId,
            @Body BilletPurchaseRequest request
    );

    @POST("api/reservations/guest")
    Call<Reservation> createReservationForGuest(
            @Query("email") String email,
            @Query("fullName") String fullName,
            @Body BilletPurchaseRequest request
    );

    @GET("api/reservations/clients/{clientId}")
    Call<List<ReservationDTO>> getReservationsByClient(@Path("clientId") Long clientId);

}