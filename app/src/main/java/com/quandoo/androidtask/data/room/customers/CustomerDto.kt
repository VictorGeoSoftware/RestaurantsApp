package com.quandoo.androidtask.data.room.customers

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.quandoo.androidtask.data.Constants.Companion.CUSTOMERS_DB


@Entity(tableName = CUSTOMERS_DB)
class CustomerDto (
        @PrimaryKey @SerializedName("id") val id: Long,
        @SerializedName("first_name") val firstName: String,
        @SerializedName("last_name") val lastName: String,
        @SerializedName("image_url") val imageUrl: String)