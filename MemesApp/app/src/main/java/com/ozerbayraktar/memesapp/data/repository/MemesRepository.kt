package com.ozerbayraktar.memesapp.data.repository

import com.ozerbayraktar.memesapp.data.service.MemesApi
import com.ozerbayraktar.memesapp.domain.data.Data
import com.ozerbayraktar.memesapp.domain.data.MemesList
import com.ozerbayraktar.memesapp.util.Resource
import javax.inject.Inject


class MemesRepository @Inject constructor(
    private val api:MemesApi
) {
    suspend fun getMemesList(): Resource<MemesList> {
        val response = try {
            api.getMemesList()
        } catch (e: Exception) {
            return Resource.Error("Something went wrong")

        }
        return Resource.Success(response)
    }

    suspend fun getMemeId(id: String): Resource<MemesList> {
        val response = try {
            api.getMeme(id)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}