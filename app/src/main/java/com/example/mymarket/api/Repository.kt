package com.example.mymarket.api

import com.example.mymarket.api.service.MarketService
import com.example.mymarket.database.AppDatabase
import com.example.mymarket.model.Resource
import com.example.mymarket.model.dataclass.Banner
import com.example.mymarket.model.dataclass.Offer
import retrofit2.Retrofit

class Repository(private val retrofit: Retrofit, private val appDatabase: AppDatabase) {

    private val marketService = retrofit.create(MarketService::class.java)

    private val responseHandler = ResponseHandler()

    suspend fun getBanners(): Resource<List<Banner>> {
        return try {
            responseHandler.handleSuccess(marketService.getBanners())
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    suspend fun getOffers(): Resource<List<Offer>> {
        return try {
            val result = responseHandler.handleSuccess(marketService.getOffers())
            appDatabase.offerDao().insertAllOffer(result.data!!)
            return result
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

}