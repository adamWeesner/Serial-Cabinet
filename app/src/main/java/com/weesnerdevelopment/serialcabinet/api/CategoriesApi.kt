package com.weesnerdevelopment.serialcabinet.api

import retrofit2.http.*
import shared.base.Response
import shared.serialCabinet.Category

interface CategoriesApi {
    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") authHeader: String
    ): Response

    @GET("categories")
    suspend fun getCategory(
        @Header("Authorization") authHeader: String,
        @Query("id") categoryId: Int
    ): Response

    @POST("categories")
    suspend fun addCategory(
        @Header("Authorization") authHeader: String,
        @Body category: Category
    ): Response

    @PUT("categories")
    suspend fun updateCategory(
        @Header("Authorization") authHeader: String,
        @Body category: Category
    ): Response

    @DELETE("categories")
    suspend fun deleteCategory(
        @Header("Authorization") authHeader: String,
        @Query("id") categoryId: Int
    ): Response
}
