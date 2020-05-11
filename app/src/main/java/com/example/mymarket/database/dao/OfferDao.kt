package com.example.mymarket.database.dao

import androidx.room.*
import com.example.mymarket.database.DatabaseColumnInfo
import com.example.mymarket.model.dataclass.Offer

@Dao
interface OfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllOffer(offerList: List<Offer>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOffer(offer: Offer)

    @Query("SELECT * FROM ${DatabaseColumnInfo.TABLE_OFFER}")
    fun getAllOffer(): List<Offer>

    @Query("SELECT * FROM ${DatabaseColumnInfo.TABLE_OFFER} WHERE ${DatabaseColumnInfo.OFFER_ID} =:id")
    fun getOfferById(id: String): Offer

    @Query("DELETE FROM ${DatabaseColumnInfo.TABLE_OFFER}")
    fun deleteAllOffer()

    @Delete
    fun deleteOffer(offer: Offer)

}