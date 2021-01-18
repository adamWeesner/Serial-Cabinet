package com.weesnerdevelopment.serialcabinet.api

import retrofit2.http.*
import shared.base.Response
import shared.serialCabinet.Manufacturer

interface ManufacturersApi {
    @GET("manufacturers")
    suspend fun getManufacturers(
        @Header("Authorization") authHeader: String
    ): Response

    @GET("manufacturers")
    suspend fun getManufacturer(
        @Header("Authorization") authHeader: String,
        @Query("id") ManufacturerId: Int
    ): Response

    @POST("manufacturers")
    suspend fun addManufacturer(
        @Header("Authorization") authHeader: String,
        @Body Manufacturer: Manufacturer
    ): Response

    @PUT("manufacturers")
    suspend fun updateManufacturer(
        @Header("Authorization") authHeader: String,
        @Body Manufacturer: Manufacturer
    ): Response

    @DELETE("manufacturers")
    suspend fun deleteManufacturer(
        @Header("Authorization") authHeader: String,
        @Query("id") ManufacturerId: Int
    ): Response
}
