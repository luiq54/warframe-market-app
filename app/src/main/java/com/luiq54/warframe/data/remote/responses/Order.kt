package com.luiq54.warframe.data.remote.responses

data class Order(
    val creation_date: String,
    val id: String,
    val last_update: String,
    val order_type: String,
    val platform: String,
    val platinum: Int,
    val quantity: Int,
    val region: String,
    val user: User,
    val visible: Boolean
)