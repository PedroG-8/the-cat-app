package com.myapps.thecatapp.data.remote

import com.myapps.thecatapp.domain.model.Cat

data class CatDto(
    val id: String,
    val url: String,
    val width: String,
    val height: String
) {
    fun toEntity() = Cat(id = id, url = url)
}