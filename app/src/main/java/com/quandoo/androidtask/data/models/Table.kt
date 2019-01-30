package com.quandoo.androidtask.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Table : Parcelable {

    @SerializedName("shape")
    var shape: String? = null
        internal set

    @SerializedName("id")
    var id: Long = 0
        internal set

    var reservedBy: String? = null
    var avatarImageReserve: String? = null

    override fun toString(): String {
        return "Table{" +
                "shape='" + shape + '\''.toString() +
                ", id=" + id +
                '}'.toString()
    }
}
