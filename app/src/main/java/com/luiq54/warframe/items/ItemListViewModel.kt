package com.luiq54.warframe.items

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luiq54.warframe.data.models.ItemListEntry
import com.luiq54.warframe.repository.WarframeRepository
import com.luiq54.warframe.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel @Inject constructor(
    private  val repository: WarframeRepository
) : ViewModel() {
    var itemList = mutableStateOf<List<ItemListEntry>>(listOf())
    var isLoading = mutableStateOf( false )

    private var cachedItemList = listOf<ItemListEntry>()
    private var isSearchStarting = true
    var isSearching = mutableStateOf(false)

    init {
        loadItems()
    }

    fun searchItemList(query: String) {
        val listToSearch = if(isSearchStarting) {
            itemList.value
        } else {
            cachedItemList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if(query.isEmpty()) {
                itemList.value = cachedItemList
                isSearching.value = false
                isSearchStarting = true
                return@launch
            }
            val results = listToSearch.filter {
                it.itemName.contains(query.trim(), ignoreCase = true)
            }
            if(isSearchStarting) {
                cachedItemList = itemList.value
                isSearchStarting = false
            }
            itemList.value = results
            isSearching.value = true
        }
    }

    fun loadItems(){
        Log.d("warframe","started loading")
        viewModelScope.launch {
            when(val result = repository.getItems()) {
                is Resource.Success -> {
                    val itemEntries = result.data!!.payload.items.mapIndexed{index, entry ->
                            ItemListEntry(itemName = entry.item_name, imageUrl = entry.thumb, itemUrl = entry.url_name)
                    }
                    itemList.value = itemEntries
                    isLoading.value = false
                    Log.d("warframe","loading done")
                }
                is Resource.Error -> {

                }
                is Resource.Loading -> {
                    isLoading.value = true
                }
            }
        }

    }
}