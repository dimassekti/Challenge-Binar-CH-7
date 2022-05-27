package com.coufie.challengechapterseven.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.asLiveData
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.datastore.UserManager


//berisi splashscreen
class MainActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    lateinit var email : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userManager = UserManager(this)

        userManager.userUsername.asLiveData().observe(this, {
            email = it.toString()
        })

        Handler(Looper.getMainLooper()).postDelayed({

//            Log.d("tes", userManager.userEmail.toString())

            if(email != ""){
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                startActivity(Intent(this, LoginActivity::class.java))
            }
//            startActivity(Intent(this, LoginActivity::class.java))


        }, 3000)
    }
}