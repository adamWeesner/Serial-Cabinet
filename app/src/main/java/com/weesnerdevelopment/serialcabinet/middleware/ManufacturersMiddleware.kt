package com.weesnerdevelopment.serialcabinet.middleware

import android.content.SharedPreferences
import com.weesnerdevelopment.frontendutils.create
import com.weesnerdevelopment.frontendutils.request
import com.weesnerdevelopment.middleware.Middleware
import com.weesnerdevelopment.serialcabinet.api.ManufacturersApi
import okhttp3.OkHttpClient
import shared.serialCabinet.Manufacturer
import shared.serialCabinet.responses.ManufacturersResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ManufacturersMiddleware @Inject constructor(
    private val prefs: SharedPreferences,
    okHttpClient: OkHttpClient
) : Middleware<Manufacturer> {
    private val httpClient = okHttpClient.create<ManufacturersApi>(SERIAL_CABINET_URL)

    private val token
        get() = prefs.getItem("token")?.asBearer
            ?: throw IllegalArgumentException("somehow token was empty..")

    override suspend fun getAll() =
        request<ManufacturersResponse?> {
            httpClient.getManufacturers(token)
        }?.items ?: listOf()

    override suspend fun getOne(queryParam: String) =
        request<ManufacturersResponse?> {
            httpClient.getManufacturer(token, queryParam.toInt())
        }?.items?.first()

    override suspend fun add(item: Manufacturer) =
        httpClient.addManufacturer(token, item)

    override suspend fun update(item: Manufacturer) =
        httpClient.updateManufacturer(token, item)

    override suspend fun remove(queryParam: String) =
        httpClient.deleteManufacturer(token, queryParam.toInt())
}
