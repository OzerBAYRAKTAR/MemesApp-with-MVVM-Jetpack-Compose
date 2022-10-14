package com.ozerbayraktar.memesapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.ozerbayraktar.memesapp.data.repository.MemesRepository
import com.ozerbayraktar.memesapp.domain.data.Data
import com.ozerbayraktar.memesapp.domain.data.MemesList
import com.ozerbayraktar.memesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MemesDetailViewModel @Inject constructor(
    private val repository: MemesRepository
) :ViewModel(){

   suspend fun getMeme(id:String) :Resource<MemesList>{
        return repository.getMemeId(id)
    }
}