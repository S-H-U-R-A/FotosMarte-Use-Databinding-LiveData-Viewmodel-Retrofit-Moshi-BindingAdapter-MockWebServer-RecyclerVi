package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private const val BASE_URL: String =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val moshi: Moshi = Moshi.Builder()
    .add( KotlinJsonAdapterFactory() )
    .build()

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(
        MoshiConverterFactory.create(moshi)
    )
    .baseUrl(BASE_URL)
    .build()


interface MarsApiService {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhoto>
}

//OBJETO SINGLETON
object MarsApi{
    val retrofitService: MarsApiService by lazy {
        retrofit.create(
            MarsApiService::class.java
        )
    }
}