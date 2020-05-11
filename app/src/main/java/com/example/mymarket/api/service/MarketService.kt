package com.example.mymarket.api.service

import com.example.mymarket.api.Url
import com.example.mymarket.model.dataclass.Banner
import com.example.mymarket.model.dataclass.Offer
import retrofit2.http.GET

interface MarketService {

    @GET(Url.GET_BANNERS)
    suspend fun getBanners(): List<Banner>

    @GET(Url.GET_OFFERS)
    suspend fun getOffers(): List<Offer>

}