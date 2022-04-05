package com.wyksofts.wyykweather.ui.helper.cloud

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.R
import com.wyksofts.wyykweather.utils.showToast

class FavoriteCity {

    val db = Firebase.firestore

    //add city to cloud
    fun addCity(cityName: String, context: Context){

        // Create a new user with a first and last name
        val city = hashMapOf(
            "cityName" to cityName,
        )

        // Add a new document with a generated ID
        db.collection("cities")
            .document(cityName)
            .set(city, SetOptions.merge())
            .addOnSuccessListener { documentReference ->
                showToast().showSuccess(context,"$cityName \t successfully added")
            }
            .addOnFailureListener { e ->
                showToast().showFailure(context,"Error adding: \t$cityName")
            }

    }

    //delete city from cloud
    fun deleteCity(cityName: String){

    }

    //get all cities
    fun getCities(city: String, favBtn: ImageView, context: Context){

        val docRef = db.collection("cities").document(city)
        docRef.get()
            .addOnSuccessListener { document ->

                if (document != null){

                    val name = document.get("cityName").toString()

                    if (name == city){
                        favBtn.setImageResource(R.drawable.baseline_favorite_24)

                        favBtn.setOnClickListener {
                            deleteCity(city)
                        }

                    }else{
                        favBtn.setImageResource(R.drawable.baseline_favorite_border_24)

                        favBtn.setOnClickListener {
                            addCity(city,context)
                            favBtn.setImageResource(R.drawable.baseline_favorite_24)
                        }

                    }
                }else{
                    favBtn.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }
            .addOnFailureListener { exception ->
                favBtn.setImageResource(R.drawable.baseline_favorite_border_24)
            }
    }

}