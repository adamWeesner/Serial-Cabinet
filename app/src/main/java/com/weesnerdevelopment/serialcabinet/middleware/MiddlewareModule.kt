package com.weesnerdevelopment.serialcabinet.middleware

import android.content.SharedPreferences
import com.weesnerdevelopment.middleware.Middleware
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import shared.serialCabinet.Category

@Module
@InstallIn(ApplicationComponent::class)
object MiddlewareModule {
    @Provides
    fun provideOccurrenceMiddleware(
        prefs: SharedPreferences,
        okHttpClient: OkHttpClient
    ): Middleware<Category> = CategoryMiddleware(prefs, okHttpClient)
}