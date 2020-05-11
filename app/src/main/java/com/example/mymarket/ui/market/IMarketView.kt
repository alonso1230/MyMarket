package com.example.mymarket.ui.market

import com.example.mymarket.model.dataclass.Banner
import com.example.mymarket.model.dataclass.Offer
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEnd
import moxy.viewstate.strategy.alias.AddToEndSingle

interface IMarketView : MvpView {

    @AddToEndSingle
    fun showInfoScreen()

    @AddToEnd
    fun showMoreBanners(bannerList: MutableList<Banner>)

    @AddToEndSingle
    fun showOffers(offerList: MutableList<Offer>)

    @AddToEndSingle
    fun showBannerLoading()

    @AddToEndSingle
    fun hideBannerLoading()

    @AddToEndSingle
    fun showBannerError(errorMessage: String)

    @AddToEnd
    fun showBannerPagingLoading()

    @AddToEnd
    fun hideBannerPagingLoading()

    @AddToEnd
    fun setBannerPagingLoading(isLoading: Boolean)

    @AddToEnd
    fun setBannerLastPage(isLastPage: Boolean)

    @AddToEndSingle
    fun showOfferLoading()

    @AddToEndSingle
    fun hideOfferLoading()

    @AddToEndSingle
    fun showOfferError(errorMessage: String)
}