package com.example.mymarket.ui.market

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymarket.R
import com.example.mymarket.base.BaseViewHolder
import com.example.mymarket.model.dataclass.Banner
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_market_banner.view.*

class MarketBannerAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val LOADING_VIEW_TYPE = 0
        private const val ITEM_VIEW_TYPE = 1
    }

    private var isLoaderVisible = false

    private var bannerList = mutableListOf<Banner>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder =
        when (viewType) {
            LOADING_VIEW_TYPE -> ProgressViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_market_banner_progress, parent, false)
            )
            else -> MarketBannerViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_market_banner, parent, false)
            )
        }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(bannerList[position], position)
    }

    override fun getItemCount() = bannerList.size

    override fun getItemViewType(position: Int) =
        if (isLoaderVisible) {
            if (position == bannerList.size - 1) LOADING_VIEW_TYPE else ITEM_VIEW_TYPE
        } else {
            ITEM_VIEW_TYPE
        }

    fun addData(dataList: MutableList<Banner>) {
        bannerList.addAll(dataList)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        bannerList.add(Banner())
        notifyItemInserted(bannerList.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position: Int = bannerList.size - 1
        bannerList.removeAt(position)
        notifyItemRemoved(position)
    }

    class MarketBannerViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun onBind(item: Any, position: Int) {
            super.onBind(item, position)
            val banner = item as Banner

            if (banner.title.isNullOrEmpty() && banner.desc.isNullOrEmpty()) {
                itemView.llBannerInfoContainer.visibility = View.GONE
            } else {
                itemView.llBannerInfoContainer.visibility = View.VISIBLE
                if (!banner.title.isNullOrEmpty()) {
                    itemView.tvBannerTitle.text = banner.title
                    itemView.tvBannerTitle.visibility = View.VISIBLE
                } else {
                    itemView.tvBannerTitle.visibility = View.GONE
                }
                if (!banner.desc.isNullOrEmpty()) {
                    itemView.tvBannerDescription.text = banner.desc
                    itemView.tvBannerDescription.visibility = View.VISIBLE
                } else {
                    itemView.tvBannerDescription.visibility = View.GONE
                }
            }

            if (!banner.image.isNullOrEmpty()) {
                Picasso.get()
                    .load(banner.image)
                    .error(R.drawable.ic_image_error_grey)
                    .into(itemView.ilvBannerImage.getImageView(), object : Callback {
                        override fun onSuccess() {
                            itemView.ilvBannerImage.completeLoading()
                        }

                        override fun onError(e: Exception?) {
                            itemView.ilvBannerImage.completeLoading()
                        }
                    })
            } else {
                itemView.ilvBannerImage.errorLoading()
            }
        }
    }

    class ProgressViewHolder(itemView: View) : BaseViewHolder(itemView)
}

