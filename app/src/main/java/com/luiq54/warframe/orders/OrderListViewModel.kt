package com.luiq54.warframe.orders

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luiq54.warframe.data.models.ItemListEntry
import com.luiq54.warframe.repository.WarframeRepository
import com.luiq54.warframe.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderListViewModel  @Inject constructor(
    private  val repository: WarframeRepository
) : ViewModel(){
    var orderList = mutableStateOf<List<ItemListEntry>>(listOf())
    var isLoading = mutableStateOf( false )


//    fun loadOrders(url_name: String){
//        Log.d("warframe","started loading")
//        viewModelScope.launch {
//            when(val result = repository.getItemOrders(url_name = url_name)) {
//                is Resource.Success -> {
//                    val itemEntries = result.data!!.payload.orders.mapIndexed{index, entry ->
//                        ItemListEntry(itemName = entry.item_name, imageUrl = entry.thumb, itemUrl = entry.url_name)
//                    }
//                    orderList.value = itemEntries
//                    isLoading.value = false
//                    Log.d("warframe","loading done")
//                }
//                is Resource.Error -> {
//
//                }
//                is Resource.Loading -> {
//                    isLoading.value = true
//                }
//            }
//        }
//
//    }
}