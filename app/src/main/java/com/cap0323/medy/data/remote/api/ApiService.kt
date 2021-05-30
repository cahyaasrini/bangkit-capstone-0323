package com.cap0323.medy.data.remote.api

import com.cap0323.medy.data.remote.response.MedicineResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("medicine/{medicine_name}")
    fun getMedicineByName(
        @Path("medicine_name") name: String
    ): Call<List<MedicineResponse>>

    @GET("medicine/{all}")
    fun getAllMedicine(
        @Path("all") all: String
    ): Call<List<MedicineResponse>>
}