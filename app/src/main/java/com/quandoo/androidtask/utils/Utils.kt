package com.quandoo.androidtask.utils

import android.app.Activity
import android.widget.Toast

fun Activity.showRequestErrorMessage(errorMessage: String) {
    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
}

fun Any.myTrace(message: String) {
    System.out.println("victor - ${this.javaClass.name} - $message")
}