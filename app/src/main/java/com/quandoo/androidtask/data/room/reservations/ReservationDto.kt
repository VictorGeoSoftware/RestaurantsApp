package com.quandoo.androidtask.data.room.reservations

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.quandoo.androidtask.data.Constants.Companion.RESERVATIONS_DB


@Entity(tableName = RESERVATIONS_DB)
class ReservationDto (@PrimaryKey @SerializedName("id") val id: Long,
                      @SerializedName("user_id") val userId: Long,
                      @SerializedName("table_id") val tableId: Long)