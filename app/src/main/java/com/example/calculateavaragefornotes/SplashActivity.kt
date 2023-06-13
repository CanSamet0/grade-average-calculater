package com.example.calculateavaragefornotes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.splash_layout.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_layout)
        val comingFromBelow = AnimationUtils.loadAnimation(this, R.anim.coming_from_below)
        buttonBelow.animation = comingFromBelow
        val comingFromTop = AnimationUtils.loadAnimation(this, R.anim.coming_from_top)
        imageViewCalculator.animation = comingFromTop
        val goToBelow = AnimationUtils.loadAnimation(this, R.anim.go_to_bellow)
        val goToTop = AnimationUtils.loadAnimation(this, R.anim.go_to_top)

        buttonBelow.setOnClickListener {
            buttonBelow.startAnimation(goToBelow)
            imageViewCalculator.startAnimation(goToTop)

            object : CountDownTimer (500, 500){
                override fun onTick(p0: Long) {}
                override fun onFinish() {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                }
            }.start()
        }
    }
}