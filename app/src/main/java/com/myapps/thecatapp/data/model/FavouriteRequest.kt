package com.myapps.thecatapp.data.model

import com.google.gson.annotations.SerializedName

data class FavouriteRequest(
    @SerializedName("image_id") val imageId: String
)
