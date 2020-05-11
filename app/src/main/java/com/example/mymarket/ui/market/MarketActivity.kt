package com.example.mymarket.ui.market

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.mymarket.R
import com.example.mymarket.base.BaseActivity
import com.example.mymarket.model.dataclass.Banner
import com.example.mymarket.model.dataclass.Offer
import com.example.mymarket.ui.info.InfoActivity
import com.example.mymarket.util.listener.PaginationListener
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar_main.*
import moxy.presenter.InjectPresenter

class MarketActivity : BaseActivity(), IMarketView {

    override val layoutRes = R.layout.activity_main

    @InjectPresenter
    internal lateinit var presenter: MarketPresenter

    private lateinit var bannerAdapter: MarketBannerAdapter
    private lateinit var offerAdapter: MarketOfferAdapter

    private var isBannerLastPage = false
    private var isBannerPagingLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbarMain)
        initAdapter()
        initListener()

        chipToolbarTopTen.callOnClick()
    }

    private fun initAdapter() {
        bannerAdapter = MarketBannerAdapter()
        LinearSnapHelper().attachToRecyclerView(rvMarketBanner)
        rvMarketBanner.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvMarketBanner.adapter = bannerAdapter

        offerAdapter = MarketOfferAdapter()
        rvMarketOffer.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvMarketOffer.adapter = offerAdapter
    }

    private fun initListener() {
        ivToolbarInfo.setOnClickListener { presenter.onInfoClicked() }
        chipToolbarTopTen.setOnClickListener { onChipClicked(chipToolbarTopTen) }
        chipToolbarMarket.setOnClickListener { onChipClicked(chipToolbarMarket) }
        chipToolbarProduct.setOnClickListener { onChipClicked(chipToolbarProduct) }
        rvMarketBanner.addOnScrollListener(object :
            PaginationListener(rvMarketBanner.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                presenter.loadMoreItems()
            }

            override fun isLastPage(): Boolean {
                return isBannerLastPage
            }

            override fun isLoading(): Boolean {
                return isBannerPagingLoading
            }
        })
    }

    private fun onChipClicked(chip: Chip) {
        when (chip) {
            chipToolbarTopTen -> {
                chipToolbarTopTen.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.blueLight)
                chipToolbarTopTen.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.blue)
                chipToolbarTopTen.setTextColor(ContextCompat.getColor(this, R.color.blue))
                chipToolbarMarket.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarMarket.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarMarket.setTextColor(ContextCompat.getColor(this, R.color.greyFour))
                chipToolbarProduct.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarProduct.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarProduct.setTextColor(ContextCompat.getColor(this, R.color.greyFour))
            }
            chipToolbarMarket -> {
                chipToolbarMarket.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.blueLight)
                chipToolbarMarket.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.blue)
                chipToolbarMarket.setTextColor(ContextCompat.getColor(this, R.color.blue))
                chipToolbarTopTen.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarTopTen.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarTopTen.setTextColor(ContextCompat.getColor(this, R.color.greyFour))
                chipToolbarProduct.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarProduct.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarProduct.setTextColor(ContextCompat.getColor(this, R.color.greyFour))
            }
            chipToolbarProduct -> {
                chipToolbarProduct.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.blueLight)
                chipToolbarProduct.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.blue)
                chipToolbarProduct.setTextColor(ContextCompat.getColor(this, R.color.blue))
                chipToolbarTopTen.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarTopTen.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarTopTen.setTextColor(ContextCompat.getColor(this, R.color.greyFour))
                chipToolbarMarket.chipBackgroundColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarMarket.chipStrokeColor =
                    ContextCompat.getColorStateList(this, R.color.white)
                chipToolbarMarket.setTextColor(ContextCompat.getColor(this, R.color.greyFour))
            }
        }
    }

    override fun showInfoScreen() {
        val intent = Intent(this, InfoActivity::class.java)
        startActivity(intent)
    }

    override fun showMoreBanners(bannerList: MutableList<Banner>) {
        bannerAdapter.addData(bannerList)
    }

    override fun showOffers(offerList: MutableList<Offer>) {
        offerAdapter.setData(offerList)
    }

    override fun showBannerLoading() {
        rvMarketBanner.visibility = View.GONE
        llMarketBannerErrorContainer.visibility = View.GONE
        pbMarketBanner.visibility = View.VISIBLE
    }

    override fun hideBannerLoading() {
        rvMarketBanner.visibility = View.VISIBLE
        llMarketBannerErrorContainer.visibility = View.GONE
        pbMarketBanner.visibility = View.GONE
    }

    override fun showBannerError(errorMessage: String) {
        rvMarketBanner.visibility = View.GONE
        llMarketBannerErrorContainer.visibility = View.VISIBLE
        pbMarketBanner.visibility = View.GONE
        tvMarketBannerErrorText.text = errorMessage
    }

    override fun showBannerPagingLoading() {
        bannerAdapter.addLoading()
    }

    override fun hideBannerPagingLoading() {
        bannerAdapter.removeLoading()
    }

    override fun setBannerPagingLoading(isLoading: Boolean) {
        isBannerPagingLoading = isLoading
    }

    override fun setBannerLastPage(isLastPage: Boolean) {
        isBannerLastPage = isLastPage
    }

    override fun showOfferLoading() {
        rvMarketOffer.visibility = View.GONE
        llMarketOfferErrorContainer.visibility = View.GONE
        pbMarketOffer.visibility = View.VISIBLE
    }

    override fun hideOfferLoading() {
        rvMarketOffer.visibility = View.VISIBLE
        llMarketOfferErrorContainer.visibility = View.GONE
        pbMarketOffer.visibility = View.GONE
    }

    override fun showOfferError(errorMessage: String) {
        rvMarketOffer.visibility = View.GONE
        llMarketOfferErrorContainer.visibility = View.VISIBLE
        pbMarketOffer.visibility = View.GONE
        tvMarketOfferErrorText.text = errorMessage
    }
}
