package com.example.mymarket.util.extension

import android.os.Build

fun String.addCurrencyRub(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        this + "₽"
    else
        this + "Р"
}