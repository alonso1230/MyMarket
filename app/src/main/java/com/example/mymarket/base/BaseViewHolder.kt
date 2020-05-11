package com.example.mymarket.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var mCurrentPosition = 0

    open fun onBind(item: Any, position: Int) {
        mCurrentPosition = position
    }

    open fun getCurrentPosition(): Int {
        return mCurrentPosition
    }
}