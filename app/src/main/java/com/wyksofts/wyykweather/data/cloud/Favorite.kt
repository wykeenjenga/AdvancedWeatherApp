package com.wyksofts.wyykweather.data.cloud

import android.content.Context
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.model.FavoriteViewModel
import com.wyksofts.wyykweather.utils.showToast

class Favorite(private val viewModel: FavoriteViewModel) {

    val db = Firebase.firestore

    //add city to cloud fav db
    fun addCity(cityName: String, context: Context) : Int{

        //city to be added
        val city = hashMapOf(
            "cityName" to cityName,
        )

        // add city
        db.collection("cities")
            .document(cityName)
            .set(city, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                showToast().showSuccess(context,"$cityName successfully added")

                viewModel.favIcon = R.drawable.baseline_favorite_24
                viewModel.currentIcon.value = viewModel.favIcon
            }
            .addOnFailureListener { e ->
                showToast().showFailure(context,"Error adding: \t$cityName")
            }

        return viewModel.favIcon
    }

    //remove city from cloud fav db
    fun deleteCity(cityName: String, context: Context): Int{

        db.collection("cities").document(cityName)
            .delete()
            .addOnSuccessListener {
                showToast().showFailure(context,"$cityName removed from fav")
                viewModel.favIcon = R.drawable.baseline_favorite_border_24
                viewModel.currentIcon.value = viewModel.favIcon
            }
            .addOnFailureListener {
                    e -> showToast().showFailure(context,"Unable to remove $cityName")
            }

        return viewModel.favIcon
    }

    //get all cities
    fun getCities(city: String){

        val docRef = db.collection("cities").document(city)
        docRef.get()
            .addOnSuccessListener { document ->

                if (document != null){

                    val name = document.get("cityName").toString()

                    if (name == city){

                        viewModel.favIcon = R.drawable.baseline_favorite_24
                        viewModel.currentIcon.value = viewModel.favIcon

                    }else{
                        viewModel.favIcon = R.drawable.baseline_favorite_border_24
                        viewModel.currentIcon.value = viewModel.favIcon
                    }
                }else{
                    viewModel.favIcon = R.drawable.baseline_favorite_border_24
                }
            }
            .addOnFailureListener { exception ->
                viewModel.favIcon  = R.drawable.baseline_favorite_border_24
            }
    }

}