package com.wyksofts.wyykweather.data.cloud

import android.content.Context
import android.util.Log
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.ui.favorite.FavoriteViewModel
import com.wyksofts.wyykweather.ui.favorite.favoriteCitiesWeather
import com.wyksofts.wyykweather.utils.showToast

class FavoriteDataModel(private val viewModel: FavoriteViewModel,
                        private val context: Context?, val favprogressBar: ProgressBar?
) {

    val db = Firebase.firestore


    //add city to cloud fav db
    fun addCity(cityName: String, context: Context) : Int{

        //city to be added
        val city = hashMapOf(cityName to cityName)

        val user = Firebase.auth.currentUser
        val email = user?.email.toString()

        // add city
        db.collection("cities")
            .document(email)
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

        val user = Firebase.auth.currentUser
        val email = user?.email.toString()

        // Remove the 'capital' field from the document
        val updates = hashMapOf<String, Any>(
            cityName to FieldValue.delete()
        )

        db.collection("cities")
            .document(email)
            .update(updates)
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



    //check if a city exist
    fun checkCityExistence(city: String){

        val user = Firebase.auth.currentUser
        val email = user?.email.toString()

        val docRef = db.collection("cities").document(email)

        docRef.get()
            .addOnSuccessListener { document ->

                if (document != null){ //check if document is empty

                    val cities = document.data //get data

                    val data = cities?.values

                    if(data != null){
                        val foundValue = data.find { item -> item == city }

//                        Log.d("Data:::>>>>>>>>>>>>>>>>>>>>>", data.toString())
//                        Log.d("foundValue:::::>>>>>>>>>>>>>>>>>>>>>", foundValue.toString())

                        if (foundValue != null) {
                            viewModel.favIcon = R.drawable.baseline_favorite_24
                            viewModel.currentIcon.value = viewModel.favIcon
                        }else{
                            viewModel.favIcon = R.drawable.baseline_favorite_border_24
                            viewModel.currentIcon.value = viewModel.favIcon
                        }
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


    //get all cities
    fun getAllCities() {

        val user = Firebase.auth.currentUser
        val email = user?.email.toString()

        favprogressBar?.isVisible = true

        val docRef = db.collection("cities").document(email)

        docRef.get()
            .addOnSuccessListener { document ->

                favprogressBar?.isVisible = true

                if (document != null){

                    val names = document.data

                    if (names != null) {

                        for (cityName in names.values){

                            val cities = listOf(cityName)
                            favprogressBar?.isVisible = false

                            if (cityName != null) {
                                favoriteCitiesWeather(viewModel, cities as List<String?>).showCitiesWeather(context,favprogressBar)
                            }
                        }
                    }

                }else{

                    showToast().showFailure(context, "Failed to fetch your favorite cities, ")
                }
            }
            .addOnFailureListener { exception ->

                showToast().showFailure(context, "Failed: Make sure you are connected. ")
            }
    }

}