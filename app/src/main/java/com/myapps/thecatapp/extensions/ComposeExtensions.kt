package com.myapps.thecatapp.extensions

fun String.csvToList(): List<String> {
    return runCatching {
        this.split(",").map { it.trim() }
    }.getOrElse {
        emptyList()
    }
}