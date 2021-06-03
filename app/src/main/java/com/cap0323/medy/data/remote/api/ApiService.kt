package com.cap0323.medy.data.remote.api

import com.cap0323.medy.data.remote.response.CategoryResponse
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

    @GET("details/{id}")
    fun getMedicineById(
        @Path("id") id: String
    ): Call<List<MedicineResponse>>

    @GET("category/{category}")
    fun getRecommendation(
        @Path("category") category: String
    ): Call<List<MedicineResponse>>

    // berdasarkan id
    @GET("recommend1/{id}")
    fun getRecommendationHeroKu(
        @Path("id") id: String
    ): Call<List<MedicineResponse>>

    @GET("keywords/{keyword}")
    fun getIndicationByChar(
        @Path("keyword") keyword: String
    ): Call<List<CategoryResponse>>

    @GET("masukkan end point nya")
    fun getMedicineByIndication(
        @Path("keyword") keyword: String
    ): Call<List<MedicineResponse>>
}