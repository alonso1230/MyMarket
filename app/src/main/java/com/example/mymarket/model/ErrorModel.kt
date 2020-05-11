package com.example.mymarket.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ErrorModel(
    val code: Int,
    override val message: String
) : Exception(), Parcelable