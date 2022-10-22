package com.example.raassigment.di

import com.example.raassigment.model.api.ApiInterface
import com.example.raassigment.utils.Constants
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun getRetrofit() : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(interceptor).build())
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofitApi(retrofit: Retrofit) : ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }


}