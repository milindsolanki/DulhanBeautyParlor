package com.example.dulhanbeautyparlor.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.dulhanbeautyparlor.R
import com.google.firebase.auth.FirebaseAuth

class SplashScreen : AppCompatActivity() {
    val SPLASH_TIME_OUT = 3000L
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        intiView()
    }

    private fun intiView() {
        Handler().postDelayed({
            auth = FirebaseAuth.getInstance()
            if (auth!!.currentUser == null) {

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right)

            }


        }, SPLASH_TIME_OUT)
    }
}