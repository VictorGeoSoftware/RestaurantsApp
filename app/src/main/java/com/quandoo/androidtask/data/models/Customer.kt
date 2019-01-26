package com.quandoo.androidtask.data.models

import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Customer : Serializable {

    @SerializedName("first_name")
    var firstName: String? = null
        internal set

    @SerializedName("last_name")
    var lastName: String? = null
        internal set

    @SerializedName("image_url")
    var imageUrl: String? = null
        internal set

    @SerializedName("id")
    var id: Long = 0
        internal set

    override fun toString(): String {
        return "Customer{" +
                "firstName='" + firstName + '\''.toString() +
                ", lastName='" + lastName + '\''.toString() +
                ", imageUrl='" + imageUrl + '\''.toString() +
                ", id=" + id +
                '}'.toString()
    }
}
