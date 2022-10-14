package com.ozerbayraktar.memesapp.presentation.view


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.ozerbayraktar.memesapp.domain.data.Meme
import com.ozerbayraktar.memesapp.presentation.viewmodel.MemesListViewModel


@Composable
fun MemesListScreen(
    navController: NavController,
    viewModel:MemesListViewModel= hiltViewModel()
) {
    Surface(
        color=MaterialTheme.colors.secondary,
        modifier=Modifier.fillMaxSize()
    ){
        Column {
            Text(
                "Best of Memes", modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 45.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchMemesList(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            MemesList(navController = navController)


        }
    }

}

@Composable
fun MemesRow(
    navController: NavController,
    meme:Meme
){
    Card(
        modifier = Modifier
            .height(70.dp)
            .padding(top = 5.dp, bottom = 5.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))


    ) {
        Row {
            Image(
                rememberImagePainter(data = meme.url),
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(50.dp, 50.dp)
                    .border(2.dp, Color.Gray)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
                    .clickable {
                        navController.navigate(
                            "meme_detail_screen/${meme.id}/${meme.name}/${meme.url}"
                        )
                    }
            ) {
                Text(
                    text = meme.name,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(2.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary
                )

            }
        }
    }
}


@Composable
fun listView(memes:List<Meme>,navController: NavController){
    LazyColumn(contentPadding = PaddingValues(5.dp)){
        items(memes){ meme ->
            MemesRow(navController = navController, meme = meme)

        }
    }

}

@Composable
fun MemesList(
    navController: NavController,
    viewModel: MemesListViewModel= hiltViewModel()

) {
    val memesList by remember{viewModel.memesList}
    val errorMessage by remember{viewModel.errorMessage}
    val isLoading by remember{viewModel.isLoading}

    listView(memes = memesList, navController = navController)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ){
        if (isLoading){
            CircularProgressIndicator(color = Color.Green)
        }
    }
}



