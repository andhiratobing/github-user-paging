package com.example.simplegithubconsumer.data


data class FavoriteUser(
    val id: Int,
    val username: String,
    val name: String?,
    val avatar: String,
    val company: String?,
    val bio: String?,
    val location: String?,
    val following: Int,
    val followers: Int,
    val repository: Int
)
