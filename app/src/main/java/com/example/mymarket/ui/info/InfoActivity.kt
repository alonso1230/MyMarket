package com.example.mymarket.ui.info

import android.os.Bundle
import com.example.mymarket.R
import com.example.mymarket.base.BaseActivity

class InfoActivity : BaseActivity() {

    override val layoutRes = R.layout.activity_info

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.info_title)
    }

}