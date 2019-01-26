package com.quandoo.androidtask.api.responses

import com.google.gson.annotations.SerializedName

data class TableResp (
        @SerializedName("id") var id: Long,
        @SerializedName("shape") var shape: String)