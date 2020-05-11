package com.example.mymarket.model

data class Resource<out T>(val result: Result, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Result.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Result.ERROR, data, msg)
        }
    }

}