package com.coufie.challengechapterseven.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.datastore.UserManager
import com.coufie.challengechapterseven.network.FilmApi
import com.coufie.challengechapterseven.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register()
        login()

    }

    fun register(){

        btn_register.setOnClickListener {

            if(et_register_password.text.isEmpty() || et_register_username.text.isEmpty()){
                Toast.makeText(this@RegisterActivity, "Data belum lengkap", Toast.LENGTH_SHORT).show()
            }else{
                if(et_register_password.text.toString() != et_confirm_password.text.toString()){
                    Toast.makeText(this@RegisterActivity, "Password tidak samaa", Toast.LENGTH_SHORT).show()
                }else{
                    val password = et_register_password.text.toString()
                    val username = et_register_username.text.toString()
                    val address = "kosong"
                    val image = "http://loremflickr.com/640/480"
                    val name = "kosong"
                    val umur = 50
                    registerUser(username,password, address, image, name, umur)
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            }

        }
    }


    fun login(){
        tv_goto_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun registerUser(username: String,password: String, address: String, image:String, name:String, umur:Int){
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.registerUser(username,password, address, image, name, umur)
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this@RegisterActivity, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
        }
    }




}