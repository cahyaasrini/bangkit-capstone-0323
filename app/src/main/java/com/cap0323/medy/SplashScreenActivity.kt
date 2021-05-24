package com.cap0323.medy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.cap0323.medy.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val delayTime = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding) {
            animationView.alpha = 0f
            text.alpha = 0f
            textDesc.alpha = 0f
            text.animate().setDuration(3000).alpha(1f)
            textDesc.animate().setDuration(4000).alpha(1f).withEndAction {
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }, delayTime)
            }
            animationView.animate().setDuration(2000).alpha(1f)
        }
    }
}