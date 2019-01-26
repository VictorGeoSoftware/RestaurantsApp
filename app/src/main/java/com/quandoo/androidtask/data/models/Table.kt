package com.quandoo.androidtask.data.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Table : Serializable {

    @SerializedName("shape")
    var shape: String? = null
        internal set

    @SerializedName("id")
    var id: Long = 0
        internal set

    var reservedBy: String? = null

    override fun toString(): String {
        return "Table{" +
                "shape='" + shape + '\''.toString() +
                ", id=" + id +
                '}'.toString()
    }
}
