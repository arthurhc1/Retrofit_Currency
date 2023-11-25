package com.arthur.pos.retrofit_currency.utils


import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

class NetworkUtils {
    companion object{
        fun getRetrofitInstance(path: String) : Retrofit {
            return Retrofit.Builder()
                .baseUrl(path)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}