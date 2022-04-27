package com.luiq54.warframe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.luiq54.warframe.items.ItemsListScreen
import com.luiq54.warframe.orders.OrdersListScreen
import com.luiq54.warframe.ui.theme.WarframeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WarframeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "search_item") {
                    composable("search_item"){
                        ItemsListScreen(navController = navController)
                    }
                    composable(
                        "item_details/{url}/{itemName}/{itemImageUrl}",
                    arguments = listOf(
                        navArgument("url"){
                            type = NavType.StringType
                        },
                        navArgument("itemName") {
                            type = NavType.StringType
                        },
                        navArgument("itemImageUrl"){
                            type = NavType.StringType
                        }
                    )
                    ){
                        val itemName = remember {
                            it.arguments!!.getString("itemName") ?: "ERROR"
                        }
                        val url = remember {
                            it.arguments!!.getString("url") ?: "ERROR"
                        }
                        val imageUrl = remember {
                            it.arguments?.getString("itemImageUrl") ?: "ERROR"
                        }
                        OrdersListScreen(
                            navController = navController,
                            itemUrl = url,
                            imageUrl = imageUrl,
                            itemName = itemName
                            )
                    }
                }
            }
        }
    }
}
