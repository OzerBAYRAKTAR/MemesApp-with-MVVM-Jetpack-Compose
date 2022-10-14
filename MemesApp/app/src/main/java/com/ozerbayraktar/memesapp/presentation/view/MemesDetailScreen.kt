package com.ozerbayraktar.memesapp.presentation.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ozerbayraktar.memesapp.domain.data.MemesList
import com.ozerbayraktar.memesapp.presentation.viewmodel.MemesDetailViewModel
import com.ozerbayraktar.memesapp.util.Resource


@OptIn(ExperimentalCoilApi::class)
@Composable
fun MemesDetailScreen(
    id:String,
    name:String,
    url:String,
    navController: NavController,
    viewModel: MemesDetailViewModel= hiltViewModel()
) {
    val memeItem = produceState<Resource<MemesList>>(initialValue = Resource.Loading()){
        value=viewModel.getMeme(id)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary),
             contentAlignment = Alignment.Center
    ){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(memeItem){
                is Resource.Success ->{

                    Text(text = name,
                        fontSize=25.sp,
                        modifier = Modifier.padding(2.dp),
                        fontWeight = FontWeight.Bold,
                        color=MaterialTheme.colors.primary,
                        textAlign = TextAlign.Center
                    )
                    Image(
                        rememberImagePainter(data =url),
                        contentDescription =null,
                        modifier = Modifier
                        .padding(16.dp)
                        .size(400.dp, 400.dp)
                        .border(2.dp, Color.Gray),
                        )



                }
                is Resource.Error -> {
                    Text(memeItem.message!!)
                }

                is Resource.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }
            }
            
        }
    }
}