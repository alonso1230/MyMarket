package com.example.mymarket.ui.market

import com.example.mymarket.R
import com.example.mymarket.base.BasePresenter
import com.example.mymarket.model.Result
import com.example.mymarket.model.dataclass.Banner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState

@InjectViewState
class MarketPresenter : BasePresenter<IMarketView>() {

    private var bannerList = mutableListOf<Banner>()

    private var currentPage = 0
    private val pageSize = 1

    override fun onFirstViewAttach() {
        sendBannersRequest()
        sendOffersRequest()
    }

    fun onInfoClicked() {
        viewState.showInfoScreen()
    }

    fun loadMoreItems() {
        viewState.setBannerPagingLoading(true)
        currentPage++;

        presenterScope.launch {
            if (currentPage > 1) {
                delay(1500) // для имитации пагинации
            }
            withContext(Dispatchers.Main) {
                if (currentPage != 1)
                    viewState.hideBannerPagingLoading()

                if (bannerList.size - currentPage * pageSize > -1) {
                    viewState.showMoreBanners(
                        bannerList.subList(
                            currentPage * pageSize - pageSize,
                            currentPage * pageSize
                        )
                    )
                } else {
                    viewState.showMoreBanners(
                        bannerList.subList(
                            currentPage * pageSize - pageSize,
                            currentPage * pageSize - 1
                        )
                    )
                }

                if (currentPage < bannerList.size / pageSize
                    || currentPage < bannerList.size / pageSize + 1
                    && bannerList.size % pageSize != 0
                ) {
                    viewState.showBannerPagingLoading()
                } else {
                    viewState.setBannerLastPage(true)
                }
                viewState.setBannerPagingLoading(false)
            }
        }
    }

    private fun sendBannersRequest() {
        viewState.showBannerLoading()
        presenterScope.launch {
            val result = repository.getBanners()
            withContext(Dispatchers.Main) {
                when (result.result) {
                    Result.SUCCESS -> {
                        if (result.data.isNullOrEmpty())
                            viewState.showBannerError(appContext.getString(R.string.market_banner_empty_list))
                        else {
                            bannerList = result.data.toMutableList()
                            loadMoreItems()
                            viewState.hideBannerLoading()
                        }
                    }
                    Result.ERROR -> {
                        viewState.showBannerError(result.message!!)
                    }
                }
            }
        }
    }

    private fun sendOffersRequest() {
        viewState.showOfferLoading()
        presenterScope.launch {
            val result = repository.getOffers()
            withContext(Dispatchers.Main) {
                when (result.result) {
                    Result.SUCCESS -> {
                        viewState.hideOfferLoading()
                        viewState.showOffers(result.data!!.toMutableList())
                    }
                    Result.ERROR -> {
                        viewState.showOfferError(result.message!!)
                    }
                }
            }
        }
    }

}