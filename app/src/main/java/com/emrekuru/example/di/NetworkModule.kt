package com.emrekuru.example.di

import androidx.viewbinding.BuildConfig
import com.emrekuru.example.network.NetworkClient
import com.emrekuru.example.util.serviceUrl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    internal fun provideGson(): Gson {
        return GsonBuilder().create()
    }
    @Provides
    internal fun provideOkHttpClient(
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(
                httpLoggingInterceptor.apply {
                    // TODO: 8.09.2020 Remove log when you create a release apk or app.
                    httpLoggingInterceptor.level = if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                }
            )
            //.addInterceptor(SupportInterceptor())
            .connectTimeout(CLIENT_TIME_OUT, TimeUnit.MINUTES)
            .writeTimeout(CLIENT_TIME_OUT, TimeUnit.MINUTES)
            .readTimeout(CLIENT_TIME_OUT, TimeUnit.MINUTES)
            .build()
    }


    @Provides
    internal fun provideRetrofitBuilder(gson: Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }


    @Provides
    fun provideNetworkClient(builder: Retrofit.Builder): NetworkClient {
        return builder.baseUrl(serviceUrl).build().create(NetworkClient::class.java)
    }

    companion object {
        private const val CLIENT_TIME_OUT = 3L //Sor?
    }
}

