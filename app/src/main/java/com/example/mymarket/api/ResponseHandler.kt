package com.example.mymarket.api

import android.content.Context
import com.example.mymarket.R
import com.example.mymarket.app.App
import com.example.mymarket.model.ErrorModel
import com.example.mymarket.model.Resource
import org.kodein.di.generic.instance
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class ResponseHandler {

    companion object {
        const val UNKNOWN = 100000
        const val TIMEOUT = 100001
        const val NOT_CONNECTION = 100002

        const val UNAUTHORISED = 401
        const val NOT_FOUND = 404
    }

    private val appContext by App.instance.kodein.instance<Context>()

    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(getErrorMessage(e.code()), null)
            is SocketTimeoutException -> Resource.error(getErrorMessage(TIMEOUT), null)
            is UnknownHostException,
            is ConnectException -> Resource.error(getErrorMessage(NOT_CONNECTION), null)
            is ErrorModel -> Resource.error(e.message, null)
            else -> Resource.error(getErrorMessage(UNKNOWN), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            TIMEOUT -> appContext.getString(R.string.error_timeout)
            NOT_CONNECTION -> appContext.getString(R.string.error_not_connection)
            UNAUTHORISED -> appContext.getString(R.string.error_unauthorised)
            NOT_FOUND -> appContext.getString(R.string.error_not_found)
            else -> appContext.getString(R.string.error_unknown)
        }
    }

}