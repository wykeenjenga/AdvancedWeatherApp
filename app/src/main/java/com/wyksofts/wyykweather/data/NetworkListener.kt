package com.wyksofts.wyykweather.data


interface NetworkListener<T> {
    fun onResult(`object`: T)
}