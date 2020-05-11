package com.example.mymarket.model.dataclass

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymarket.database.DatabaseColumnInfo
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = DatabaseColumnInfo.TABLE_OFFER)
data class Offer(
    @PrimaryKey
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_ID)
    val id: String,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_NAME)
    val name: String,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_GROUP_NAME)
    val groupName: String,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_TYPE)
    val type: String,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_DESC)
    val desc: String?,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_IMAGE)
    val image: String?,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_PRICE)
    val price: Float?,
    @ColumnInfo(name = DatabaseColumnInfo.OFFER_DISCOUNT)
    val discount: Float?
) : Parcelable {
    companion object {
        const val PRODUCT = "product"
        const val ITEM = "item"
    }
}