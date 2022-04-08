package com.wyksofts.wyykweather.ui.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {

    var googleId =  ""
    var googleFirstName = ""
    var googleLastName = ""
    var googleEmail = ""
    var googleIdToken =  ""

    val currentUser : MutableLiveData<String> by lazy{
        MutableLiveData<String>()
    }

}