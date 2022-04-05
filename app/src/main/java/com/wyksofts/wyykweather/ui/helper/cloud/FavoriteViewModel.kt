package com.wyksofts.wyykweather.ui.helper.cloud

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wyksofts.wyykweather.R

class FavoriteViewModel : ViewModel() {

    var favIcon = R.drawable.baseline_favorite_border_24

    val currentIcon : MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }


}

