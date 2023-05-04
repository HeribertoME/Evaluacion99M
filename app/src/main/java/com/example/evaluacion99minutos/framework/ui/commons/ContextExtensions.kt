package com.example.evaluacion99minutos.framework.ui.commons

import android.content.Context
import android.widget.Toast

fun Context.toast(message: CharSequence, isShort: Boolean = true) =
    Toast.makeText(
        this, message, if (isShort) {
            Toast.LENGTH_SHORT
        } else {
            Toast.LENGTH_LONG
        }
    ).show()