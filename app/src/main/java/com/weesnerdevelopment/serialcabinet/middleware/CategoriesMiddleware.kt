package com.weesnerdevelopment.serialcabinet.middleware

import android.content.SharedPreferences
import com.weesnerdevelopment.frontendutils.create
import com.weesnerdevelopment.frontendutils.request
import com.weesnerdevelopment.middleware.Middleware
import com.weesnerdevelopment.serialcabinet.api.CategoriesApi
import okhttp3.OkHttpClient
import shared.serialCabinet.Category
import shared.serialCabinet.responses.CategoriesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryMiddleware @Inject constructor(
    private val prefs: SharedPreferences,
    okHttpClient: OkHttpClient
) : Middleware<Category> {
    private val httpClient = okHttpClient.create<CategoriesApi>(SERIAL_CABINET_URL)

    private val token
        get() = prefs.getItem("token")?.asBearer
            ?: throw IllegalArgumentException("somehow token was empty..")

    override suspend fun getAll() =
        request<CategoriesResponse?> {
            httpClient.getCategories(token)
        }?.items ?: listOf()

    override suspend fun getOne(queryParam: String) =
        request<CategoriesResponse?> {
            httpClient.getCategory(token, queryParam.toInt())
        }?.items?.first()

    override suspend fun add(item: Category) =
        httpClient.addCategory(token, item)

    override suspend fun update(item: Category) =
        httpClient.updateCategory(token, item)

    override suspend fun remove(queryParam: String) =
        httpClient.deleteCategory(token, queryParam.toInt())
}
