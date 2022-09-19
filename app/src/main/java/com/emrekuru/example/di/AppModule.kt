package com.emrekuru.example.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    internal fun provideSharedPreferences(
        context: Context
    ): SharedPreferences {
        return context.getSharedPreferences("sharedPref123", Context.MODE_PRIVATE)
    }
}
