package com.example.uijasonwebservice.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ServicesMain {
    @GET("bankList")
    Call<List<ResponseRetrofit>> getNamesBanks(@Header("Public-Merchant-Id") String token);
}
