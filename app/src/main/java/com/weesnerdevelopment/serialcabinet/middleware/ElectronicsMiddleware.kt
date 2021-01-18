package com.weesnerdevelopment.serialcabinet.middleware

import android.content.SharedPreferences
import com.weesnerdevelopment.frontendutils.create
import com.weesnerdevelopment.frontendutils.request
import com.weesnerdevelopment.middleware.Middleware
import com.weesnerdevelopment.serialcabinet.api.ElectronicsApi
import okhttp3.OkHttpClient
import shared.serialCabinet.Electronic
import shared.serialCabinet.responses.ElectronicsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ElectronicsMiddleware @Inject constructor(
    private val prefs: SharedPreferences,
    okHttpClient: OkHttpClient
) : Middleware<Electronic> {
    private val httpClient = okHttpClient.create<ElectronicsApi>(SERIAL_CABINET_URL)

    private val token
        get() = prefs.getItem("token")?.asBearer
            ?: throw IllegalArgumentException("somehow token was empty..")

    override suspend fun getAll() =
        request<ElectronicsResponse?> {
            httpClient.getElectronics(token)
        }?.items ?: listOf()

    override suspend fun getOne(queryParam: String) =
        request<ElectronicsResponse?> {
            httpClient.getElectronic(token, queryParam.toInt())
        }?.items?.first()

    override suspend fun add(item: Electronic) =
        httpClient.addElectronic(token, item)

    override suspend fun update(item: Electronic) =
        httpClient.updateElectronic(token, item)

    override suspend fun remove(queryParam: String) =
        httpClient.deleteElectronic(token, queryParam.toInt())
}
