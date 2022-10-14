package com.ozerbayraktar.memesapp.data.service

import com.ozerbayraktar.memesapp.domain.data.Data
import com.ozerbayraktar.memesapp.domain.data.MemesList
import retrofit2.http.GET
import retrofit2.http.Query

interface MemesApi {

    @GET("/get_memes")
    suspend fun getMemesList(): MemesList

    @GET("/get_memes")
    suspend fun getMeme(
        @Query("ids") id:String
    ):MemesList
}