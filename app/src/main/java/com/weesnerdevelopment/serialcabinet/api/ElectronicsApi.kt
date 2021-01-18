package com.weesnerdevelopment.serialcabinet.api

import retrofit2.http.*
import shared.base.Response
import shared.serialCabinet.Electronic

interface ElectronicsApi {
    @GET("electronics")
    suspend fun getElectronics(
        @Header("Authorization") authHeader: String
    ): Response

    @GET("electronics")
    suspend fun getElectronic(
        @Header("Authorization") authHeader: String,
        @Query("id") electronicId: Int
    ): Response

    @POST("electronics")
    suspend fun addElectronic(
        @Header("Authorization") authHeader: String,
        @Body electronic: Electronic
    ): Response

    @PUT("electronics")
    suspend fun updateElectronic(
        @Header("Authorization") authHeader: String,
        @Body electronic: Electronic
    ): Response

    @DELETE("electronics")
    suspend fun deleteElectronic(
        @Header("Authorization") authHeader: String,
        @Query("id") electronicId: Int
    ): Response
}
