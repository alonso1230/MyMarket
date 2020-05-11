package com.example.mymarket.model.dataclass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
    val id: String,
    val title: String?,
    val desc: String?,
    val image: String?
) : Parcelable {
    constructor() : this("", null, null, null)
}