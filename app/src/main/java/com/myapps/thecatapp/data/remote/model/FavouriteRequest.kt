package com.myapps.thecatapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class FavouriteRequest(
    @SerializedName("image_id") val imageId: String
)
