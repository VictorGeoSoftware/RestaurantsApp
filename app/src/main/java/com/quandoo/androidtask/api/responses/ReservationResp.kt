package com.quandoo.androidtask.api.responses

import com.google.gson.annotations.SerializedName

data class ReservationResp (
        @SerializedName("user_id") val userId: Long,
        @SerializedName("table_id") val tableId: Long,
        @SerializedName("id") val id: Long)