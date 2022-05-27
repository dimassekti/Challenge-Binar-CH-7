package com.coufie.challengechapterseven.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.datastore.UserManager
import com.coufie.challengechapterseven.model.GetAllUserItem
import com.coufie.challengechapterseven.network.FilmApi
import com.coufie.challengechapterseven.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    private lateinit var userLogin : List<GetAllUserItem>

    var id = ""
    var password = ""
    var username = ""



    lateinit var dataUser : List<GetAllUserItem>
    lateinit var viewModel : UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //init
        userManager = UserManager(this)

        getUserData()
        register()
        login()

    }

    fun login(){
        btn_login.setOnClickListener {
            if(et_input_username.text.isNotEmpty() && et_input_password.text.isNotEmpty()){
                username = et_input_username.text.toString()
                password = et_input_password.text.toString()

                login(username, password)
            }else{
                Toast.makeText(this, "Mohon isi form login", Toast.LENGTH_SHORT).show()

            }
        }
    }


    fun login(username : String, password : String){
        FilmApi.instance.getUser(username)
            .enqueue(object : retrofit2.Callback<List<GetAllUserItem>>{
                override fun onResponse(
                    call: Call<List<GetAllUserItem>>,
                    response: Response<List<GetAllUserItem>>
                ) {
                    if(response.isSuccessful){
                        if(response.body()?.isEmpty() == true){
                            Toast.makeText(this@LoginActivity, "User tidak ditemukan", Toast.LENGTH_SHORT).show()
                        }else{
                            when{
                                response.body()?.size!! > 1 -> {
                                    Toast.makeText(this@LoginActivity, "Mohon masukan data yang benar", Toast.LENGTH_SHORT).show()
                                }
                                username!=response.body()!![0].username -> {
                                    Toast.makeText(this@LoginActivity, "Email tidak terdaftar", Toast.LENGTH_SHORT).show()
                                }
                                password!=response.body()!![0].password -> {
                                    Toast.makeText(this@LoginActivity, "Password Salah", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    userLogin = response.body()!!
                                    detailUser(userLogin)

                                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))

                                }
                            }
                        }
                    }else{
                        Toast.makeText(this@LoginActivity, response.message(), Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<List<GetAllUserItem>>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_SHORT).show()
                }

            })
    }

    fun detailUser(listLogin : List<GetAllUserItem>){
        for(i in listLogin.indices){
            GlobalScope.launch {
                userManager!!.saveData(
                    listLogin[i].id,
                    listLogin[i].username,
                    listLogin[i].password,
                    listLogin[i].name,
                    listLogin[i].umur.toString(),
                    listLogin[i].image.toString(),
                    listLogin[i].address

                )
            }
        }
    }


    fun register(){
        tv_goto_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }



    fun getUserData(){
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        viewModel.getUserLiveDataObserver().observe(this, Observer {
            dataUser = it
        })
        viewModel.loginUser()
    }


    override fun onResume() {
        super.onResume()
        getUserData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}