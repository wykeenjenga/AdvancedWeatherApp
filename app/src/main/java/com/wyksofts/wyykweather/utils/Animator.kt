package com.wyksofts.wyykweather.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.widget.ImageView

class Animator {

    fun animate(image: ImageView, from: Float, to: Float, duration: Long){

        val scaleX = ObjectAnimator.ofFloat(image, "scaleX", from, to)
        val scaleY = ObjectAnimator.ofFloat(image, "scaleY", from, to)

        scaleX.repeatCount = ObjectAnimator.INFINITE
        scaleX.repeatMode = ObjectAnimator.REVERSE

        scaleY.repeatCount = ObjectAnimator.INFINITE
        scaleY.repeatMode = ObjectAnimator.REVERSE

        val scaleAnim = AnimatorSet()
        scaleAnim.duration = duration
        scaleAnim.play(scaleX).with(scaleY)

        scaleAnim.start()

    }

}