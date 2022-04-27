package com.luiq54.warframe.data.remote

import com.luiq54.warframe.data.remote.responses.Items
import com.luiq54.warframe.data.remote.responses.Orders
import retrofit2.http.GET
import retrofit2.http.Path

interface WarframeApi {

    @GET("items")
    suspend fun getItems(): Items

    @GET("items/{url_name}/orders")
    suspend fun getItemOrders(
        @Path("url_name") url_name: String
    ): Orders
}