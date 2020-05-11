package com.example.mymarket.util.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.example.mymarket.R

class ImageLoadView : RelativeLayout {

    private var imageView: ImageView
    private var progressBar: ProgressBar

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inflate(context, R.layout.view_image_load, this)

        imageView = findViewById(R.id.ivImageLoad)
        progressBar = findViewById(R.id.pbImageLoad)
    }

    fun startLoading() {
        imageView.visibility = GONE
        progressBar.visibility = VISIBLE
    }

    fun completeLoading() {
        progressBar.visibility = GONE
        imageView.visibility = VISIBLE
    }

    fun errorLoading() {
        progressBar.visibility = GONE
        imageView.visibility = VISIBLE
        imageView.setImageResource(R.drawable.ic_image_error_grey)
    }

    fun getImageView(): ImageView {
        return imageView
    }

    fun getProgressBar(): ProgressBar {
        return progressBar
    }

}