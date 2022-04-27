package com.luiq54.warframe.data.remote.responses

data class User(
    val avatar: String,
    val id: String,
    val ingame_name: String,
    val last_seen: String,
    val region: String,
    val reputation: Int,
    val status: String
)