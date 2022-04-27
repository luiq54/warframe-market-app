package com.luiq54.warframe.repository

import com.luiq54.warframe.data.remote.WarframeApi
import com.luiq54.warframe.data.remote.responses.Items
import com.luiq54.warframe.data.remote.responses.Orders
import com.luiq54.warframe.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class WarframeRepository @Inject constructor(
    private val api: WarframeApi
) {

    suspend fun getItems() : Resource<Items>{
        val response = try {
            api.getItems()
        } catch (e : Exception){
            return Resource.Error("unknown error")
        }
        return Resource.Success(response)
    }

    suspend fun getItemOrders(url_name: String) : Resource<Orders>{
        val response = try {
            api.getItemOrders(url_name)
        } catch (e : Exception){
            return Resource.Error("unknown error")
        }
        return Resource.Success(response)
    }
}