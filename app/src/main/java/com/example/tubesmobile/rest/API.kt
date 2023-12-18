package com.example.tubesmobile.rest

import com.example.tubesmobile.data.*
import retrofit2.Call
import retrofit2.http.*

interface API {
    @GET("read.php")
    fun getCountries():Call<ArrayList<CountryItem>>

    @GET("detail.php")
    fun getCountryDetail(
        @Query("countryId") countryId: String?
    ):Call<CountryDetail>

    @FormUrlEncoded
    @POST("add.php")
    fun addCountryDetail(
        @Field("country_name") country_name: String?,
        @Field("country_continent") country_continent: String?,
        @Field("country_population") country_population: String?,
        @Field("country_description") country_description: String?
    ): Call<Response>

    @FormUrlEncoded
    @POST("update.php")
    fun updateCountryDetail(
        @Field("country_id") country_id: String?,
        @Field("country_name") country_name: String?,
        @Field("country_continent") country_continent: String?,
        @Field("country_population") country_population: String?,
        @Field("country_description") country_description: String?
    ): Call<Response>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteCountryDetail(
        @Field("country_id") country_id: String?
    ): Call<Response>
}