package com.wyksofts.wyykweather.ui.user

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wyksofts.wyykweather.ui.main.HomeFragment
import com.wyksofts.wyykweather.ui.main.MainActivity
import com.wyksofts.wyykweather.utils.showToast

class ContinueWithGoogle(val activity: Activity) {

    private var auth = Firebase.auth

    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>,context: Context) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            val googleIdToken = account?.idToken ?: ""

            when {googleIdToken != null -> {
                // Got an ID token from Google. Use it to authenticate
                // with Firebase.
                val firebaseCredential = GoogleAuthProvider.getCredential(googleIdToken, null)
                auth.signInWithCredential(firebaseCredential)
                    .addOnCompleteListener(activity) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser

                            (activity as MainActivity?)?.showFragment(HomeFragment(), false)

                            showToast().showSuccess(context, "Signed in successfully")

                        } else {
                            // If sign in fails, display a message to the user
                            showToast().showFailure(context, "signInWithCredential:failure")
                        }
                    }
            }else -> {
                // Shouldn't happen.
                Log.d(ContentValues.TAG, "No ID token!")
            }
            }

        } catch (e: ApiException) {
            // Sign in was unsuccessful
            showToast().showFailure(context, e.message.toString())
        }
    }
}