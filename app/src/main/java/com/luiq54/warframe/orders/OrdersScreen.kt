package com.luiq54.warframe.orders

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luiq54.warframe.util.Constants.BASE_IMAGE_URL
import com.luiq54.warframe.util.Constants.SURFACE_COLOR
import java.net.URLDecoder

@Composable
fun OrdersListScreen(
    navController: NavController,
    itemName: String,
    itemUrl: String,
    imageUrl: String,
    viewModel: OrderListViewModel = hiltViewModel()
) {
    Surface(
        color = SURFACE_COLOR,
        modifier = Modifier.fillMaxSize()
    ) {
        val decodedImageUrl = URLDecoder.decode(imageUrl, "UTF-8")
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                .data(BASE_IMAGE_URL + imageUrl)
                .build(),
                contentDescription = "item",
                modifier = Modifier
                    .size(256.dp)
                    .fillMaxWidth()
                    .align(CenterHorizontally)

            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                itemName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()


            )
        }
    }
}

