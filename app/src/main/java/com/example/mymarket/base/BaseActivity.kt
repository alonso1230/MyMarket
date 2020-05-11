package com.example.mymarket.base

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import moxy.MvpAppCompatActivity

abstract class BaseActivity : MvpAppCompatActivity() {

    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}