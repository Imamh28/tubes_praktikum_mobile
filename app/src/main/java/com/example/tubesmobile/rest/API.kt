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
        @Field("task_name") task_name: String?,
        @Field("subject_name") subject_name: String?,
        @Field("task_deadline") task_deadline: String?,
        @Field("task_description") task_description: String?
    ): Call<Response>

    @FormUrlEncoded
    @POST("update.php")
    fun updateCountryDetail(
        @Field("country_id") country_id: String?,
        @Field("task_name") task_name: String?,
        @Field("subject_name") subject_name: String?,
        @Field("task_deadline") task_deadline: String?,
        @Field("task_description") task_description: String?
    ): Call<Response>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteCountryDetail(
        @Field("country_id") country_id: String?
    ): Call<Response>
}