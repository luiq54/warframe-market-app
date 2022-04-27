package com.luiq54.warframe.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.luiq54.warframe.R
import com.luiq54.warframe.data.models.ItemListEntry
import com.luiq54.warframe.util.Constants.SURFACE_COLOR
import java.net.URLEncoder

@Composable
fun ItemsListScreen(
    navController: NavController,
    viewModel: ItemListViewModel = hiltViewModel()
) {
    Surface(
        color = SURFACE_COLOR,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_warframe_logo),
                contentDescription = "Warframe",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(CenterHorizontally)

            )
            SearchBar(
                hint = "Search",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                viewModel.searchItemList(it)
            }
            Spacer(modifier = Modifier.height(16.dp))
            ItemList(navController = navController)
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }

    Box(modifier = modifier) {
        BasicTextField(
            value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused
                }
        )
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }
}

@Composable
fun ItemList(
    navController: NavController,
    viewModel: ItemListViewModel = hiltViewModel()
) {
    val itemList by remember { viewModel.itemList }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if(itemList.size % 2 == 0) {
            itemList.size / 2
        } else {
            itemList.size / 2 + 1
        }
        items(itemCount) {
            ItemRow(rowIndex = it, entries = itemList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
    }

}

@Composable
fun ItemEntry (
    entry : ItemListEntry,
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: ItemListViewModel = hiltViewModel()
){

    Box(
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(Color.White)
            .clip(RoundedCornerShape(10.dp))
//            .background(Brush.ve)
            .clickable {
                val encodedImageUrl  = URLEncoder.encode(entry.imageUrl, "UTF-8")
                navController.navigate(
                    "item_details/${entry.itemUrl}/${entry.itemName}/${encodedImageUrl}"
                )
            }
    ) {
        Column {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://warframe.market/static/assets/" + entry.imageUrl)
                    .build(),
                contentDescription = entry.itemName,
//                fadeIn = true,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
            )
            Text(
                text = entry.itemName,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()

            )
        }
    }

}
@Composable
fun ItemRow(
    rowIndex: Int,
    entries: List<ItemListEntry>,
    navController: NavController
) {
    Column {
        Row {
            ItemEntry(
                entry = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if(entries.size >= rowIndex * 2 + 2) {
                ItemEntry(
                    entry = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}