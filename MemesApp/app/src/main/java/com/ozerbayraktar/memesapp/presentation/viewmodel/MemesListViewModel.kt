package com.ozerbayraktar.memesapp.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozerbayraktar.memesapp.data.repository.MemesRepository
import com.ozerbayraktar.memesapp.domain.data.Meme
import com.ozerbayraktar.memesapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MemesListViewModel @Inject constructor(
    private val repository: MemesRepository
): ViewModel(){

    var memesList= mutableStateOf<List<Meme>>(listOf())
    var errorMessage= mutableStateOf("")
    var isLoading= mutableStateOf(false)

    init {
        loadMemes()
    }


    //her yeni harf yazılıp silindiğinde tekrar tekrar liste indirilmesin diye, indirilen liste initialmemeliste kaydedlip arama yapılırken burdan çekilecek.
    private var initialMemesList= listOf<Meme>()
    private var isSearchStarting=true




    fun searchMemesList(query:String) {
        val listToSearch = if (isSearchStarting) {
            memesList.value
        } else {
            initialMemesList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                memesList.value=initialMemesList
                isSearchStarting=true
                return@launch
            }
            //eğer arama yapma yerine bir şeyler yazdıysa
            val results=listToSearch.filter{
                it.name.contains(query.trim(),ignoreCase = true)
            }

            //ilk defa arama başlıyorsa,
            if (isSearchStarting) {
                initialMemesList=memesList.value
                isSearchStarting=false
            }
            memesList.value=results

        }

    }

    fun loadMemes() {
        viewModelScope.launch {
            isLoading.value=true
            val result=repository.getMemesList()


            when(result){

                is Resource.Success ->{

                    val memesItems=result.data!!.data.memes.mapIndexed { index, item ->
                        Meme(item.box_count,item.height,item.id,item.name,item.url,item.width)
                    }


                    errorMessage.value=""
                    isLoading.value=false
                    memesList.value+=memesItems


                }
                is Resource.Error -> {
                    errorMessage.value=("something went wrong")
                    isLoading.value=false
                    println("veri gelmedi")
                }

            }
        }
    }
}