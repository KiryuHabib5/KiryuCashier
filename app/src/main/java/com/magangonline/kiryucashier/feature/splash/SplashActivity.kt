package com.magangonline.kiryucashier.feature.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.magangonline.kiryucashier.feature.Home.Dashboard.MainActivity
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.Home.Dashboard.HomeActivity
import com.magangonline.kiryucashier.feature.start.StartActivity
import com.magangonline.kiryucashier.model.User

class SplashActivity : AppCompatActivity() {
    private lateinit var  auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = Firebase.auth
        val currentUser = auth?.currentUser
        val handler = Handler()

        if (currentUser == null){
            handler.postDelayed(Runnable {
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }, 2000)
        }else {
            handler.postDelayed(Runnable {
                val intent = Intent(this, HomeActivity::class.java)
                //intent.putExtra("email", currentUser.email)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
                finish()
            }, 2000)
        }


    }

}