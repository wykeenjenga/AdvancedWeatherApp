package com.wyksofts.wyykweather.ui.helper.cloud

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FavoriteCity {

    val db = Firebase.firestore

    //add city to cloud
    fun addCity(cityName: String){

        // Create a new user with a first and last name
        val city = hashMapOf(
            cityName to cityName,
        )

        // Add a new document with a generated ID
        db.collection("cities")
            .add(city)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }

    }

    //delete city from cloud
    fun deleteCity(cityName: String){

    }

    //get all cities
    fun getCities(){

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

    }

}