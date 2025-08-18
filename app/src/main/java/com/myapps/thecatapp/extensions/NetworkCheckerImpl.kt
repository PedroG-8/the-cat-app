package com.myapps.thecatapp.extensions

import android.content.Context

class NetworkCheckerImpl(private val context: Context) : NetworkChecker {
    override fun isOnline() = context.isOnline()
}