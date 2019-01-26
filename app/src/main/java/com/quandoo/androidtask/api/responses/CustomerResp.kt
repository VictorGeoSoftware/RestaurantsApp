package com.quandoo.androidtask.api.responses

import com.google.gson.annotations.SerializedName

data class CustomerResp(
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("image_url") val imageUrl: String,
        @SerializedName("id") val id: Long)