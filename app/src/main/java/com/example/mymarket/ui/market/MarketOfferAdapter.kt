package com.example.mymarket.ui.market

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.example.mymarket.R
import com.example.mymarket.model.dataclass.Offer
import com.example.mymarket.util.extension.addCurrencyRub
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_market_offer.view.*
import kotlin.math.roundToInt

class MarketOfferAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var offerList = mutableListOf<Offer>()
    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketOfferViewHolder =
        MarketOfferViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_market_offer, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val offer = offerList[position]

        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.itemView.srlOfferContainer, offer.id)
        viewBinderHelper.closeLayout(offer.id)

        if (position == 0) {
            holder.itemView.tvOfferHeader.text = offer.groupName
            holder.itemView.llOfferHeader.visibility = View.VISIBLE
        } else {
            if (offerList[position - 1].groupName.equals(offer.groupName, ignoreCase = true)) {
                holder.itemView.llOfferHeader.visibility = View.GONE
            } else {
                holder.itemView.tvOfferHeader.text = offer.groupName
                holder.itemView.llOfferHeader.visibility = View.VISIBLE
            }
        }

        if (offer.name.isEmpty()) {
            holder.itemView.tvOfferTitle.visibility = View.GONE
        } else {
            holder.itemView.tvOfferTitle.text = offer.name
            holder.itemView.tvOfferTitle.visibility = View.VISIBLE
        }

        holder.itemView.tvOfferDesc.text = offer.desc

        if (offer.type == Offer.PRODUCT) {
            holder.itemView.ivOfferBasket.visibility = View.VISIBLE
            if (offer.discount != null && offer.discount > 0) {
                holder.itemView.tvOfferDiscount.text = "-${(100 * offer.discount).roundToInt()}%"
                holder.itemView.tvOfferDiscount.visibility = View.VISIBLE
            } else {
                holder.itemView.tvOfferDiscount.visibility = View.GONE
            }
            if (offer.price != null && offer.price > 0) {
                holder.itemView.llOfferPriceContainer.visibility = View.VISIBLE
                if (offer.discount != null && offer.discount > 0) {
                    holder.itemView.tvOfferPrice.text =
                        "${offer.price.roundToInt()} ".addCurrencyRub()
                    holder.itemView.tvOfferPrice.paintFlags =
                        holder.itemView.tvOfferPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    holder.itemView.tvOfferPriceDiscount.text =
                        "${(offer.price - offer.price * offer.discount).roundToInt()} ".addCurrencyRub()
                } else {
                    holder.itemView.tvOfferPrice.text = ""
                    holder.itemView.tvOfferPriceDiscount.text =
                        "${offer.price.roundToInt()} ".addCurrencyRub()
                }
            } else {
                holder.itemView.llOfferPriceContainer.visibility = View.GONE
            }
        } else {
            holder.itemView.ivOfferBasket.visibility = View.GONE
            holder.itemView.tvOfferDiscount.visibility = View.GONE
            holder.itemView.llOfferPriceContainer.visibility = View.GONE
        }

        if (!offer.image.isNullOrEmpty()) {
            Picasso.get()
                .load(offer.image)
                .error(R.drawable.ic_image_error_grey)
                .into(holder.itemView.ilvOfferImage.getImageView(), object : Callback {
                    override fun onSuccess() {
                        holder.itemView.ilvOfferImage.completeLoading()
                    }

                    override fun onError(e: Exception?) {
                        holder.itemView.ilvOfferImage.completeLoading()
                    }
                })
        } else {
            holder.itemView.ilvOfferImage.errorLoading()
        }
    }

    override fun getItemCount() = offerList.size

    fun setData(dataList: MutableList<Offer>) {
        sortOfferList(dataList)
        notifyDataSetChanged()
    }

    private fun sortOfferList(dataList: MutableList<Offer>) {
        val groupNameList = mutableListOf<String>()
        dataList.forEach {
            if (!groupNameList.contains(it.groupName)) {
                groupNameList.add(it.groupName)
            }
        }
        offerList = mutableListOf()
        groupNameList.forEach {
            for (offer in dataList) {
                if (it.equals(offer.groupName, ignoreCase = true)) {
                    offerList.add(offer)
                }
            }
        }
    }

    class MarketOfferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}