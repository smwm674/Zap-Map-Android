package com.zapmap.task.ui.splash_activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.zapmap.task.R
import com.zapmap.task.databinding.ActivitySplashBinding
import com.zapmap.task.ui.main_activity.MainActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var activitySplashBinding: ActivitySplashBinding
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySplashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(activitySplashBinding.root)
        context = this@SplashActivity
        startAnimations()
        loadScreen()
    }

    private fun loadScreen() {
        //switch to main activity after the delay of 3 secs
        Handler(Looper.getMainLooper()).postDelayed({
            val splashIntent = Intent(context, MainActivity::class.java)
            startActivity(splashIntent)
            finish()
        }, 3000)
    }

    private fun startAnimations() {
        //apply alpha animation on the constraintLayout in the activity's xml file
        var animationUtils = AnimationUtils.loadAnimation(context, R.anim.alpha)
        animationUtils.reset()
        activitySplashBinding.constraintLayout.clearAnimation()
        activitySplashBinding.constraintLayout.startAnimation(animationUtils)
        //apply translate animation on the imageView in the xml file
        animationUtils = AnimationUtils.loadAnimation(context, R.anim.translate)
        animationUtils.reset()
        activitySplashBinding.splashLogo.clearAnimation()
        activitySplashBinding.splashLogo.startAnimation(animationUtils)
    }
}