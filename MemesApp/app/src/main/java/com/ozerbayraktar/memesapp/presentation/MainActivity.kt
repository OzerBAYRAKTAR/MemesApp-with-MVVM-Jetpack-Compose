package com.ozerbayraktar.memesapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.ozerbayraktar.memesapp.presentation.ui.theme.MemesAppTheme
import com.ozerbayraktar.memesapp.presentation.view.MemesDetailScreen
import com.ozerbayraktar.memesapp.presentation.view.MemesListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MemesAppTheme {
                val navController= rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "meme_list_screen"
                     ){
                    composable("meme_list_screen"){
                        MemesListScreen(navController = navController)
                    }
                    composable(
                        "meme_detail_screen/{memeId}/{memeName}/{memeUrl}",
                        arguments = listOf(
                            navArgument("memeId"){
                                type= NavType.StringType
                            },
                            navArgument("memeName"){
                                type=NavType.StringType
                            },
                            navArgument("memeUrl"){
                                type=NavType.StringType
                            }
                        )
                    ){
                        val memeId=remember {
                            it.arguments?.getString("memeId")
                        }
                        val memeName=remember{
                            it.arguments?.getString("memeName")
                        }
                        val memeUrl=remember{
                            it.arguments?.getString("memeUrl")
                        }

                        MemesDetailScreen(
                            id = memeId?: "",
                            name = memeName?: "",
                            url = memeUrl?: "",
                            navController=navController

                        )

                    }}



            }
        }
    }


}
