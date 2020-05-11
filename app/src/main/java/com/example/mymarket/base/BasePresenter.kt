package com.example.mymarket.base

import android.content.Context
import com.example.mymarket.api.Repository
import com.example.mymarket.app.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import moxy.MvpPresenter
import moxy.MvpView
import org.kodein.di.generic.instance

abstract class BasePresenter<T : MvpView> : MvpPresenter<T>() {

    protected val presenterScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    protected val appContext by App.instance.kodein.instance<Context>()
    protected val repository by App.instance.kodein.instance<Repository>()

    override fun onDestroy() {
        presenterScope.cancel()
        super.onDestroy()
    }

}