package com.quandoo.androidtask.data.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Reservation(userId: Long, tableId: Long, id: Long) : Serializable {

    @SerializedName("user_id")
    var userId: Long = 0
        internal set

    @SerializedName("table_id")
    var tableId: Long = 0
        internal set

    @SerializedName("id")
    var id: Long = 0
        internal set


    init {
        this.userId = userId
        this.tableId = tableId
        this.id = id
    }

    override fun toString(): String {
        return "Reservation{" +
                "userId=" + userId +
                ", tableId=" + tableId +
                ", id=" + id +
                '}'.toString()
    }
}
