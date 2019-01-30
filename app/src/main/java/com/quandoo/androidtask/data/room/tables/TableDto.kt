package com.quandoo.androidtask.data.room.tables

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.quandoo.androidtask.data.Constants.Companion.TABLES_DB


@Entity(tableName = TABLES_DB)
class TableDto (
        @PrimaryKey @SerializedName("id") val id: Long,
        @SerializedName("shape") var shape: String?,
        @SerializedName("reservedBy") var reservedBy: String?,
        @SerializedName("avatarImageReserve") var avatarImageReserve: String?)