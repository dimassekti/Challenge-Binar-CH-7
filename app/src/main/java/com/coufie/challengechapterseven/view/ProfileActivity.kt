package com.coufie.challengechapterseven.view

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.datastore.UserManager
import com.coufie.challengechapterseven.network.FilmApi
import com.coufie.challengechapterseven.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {

    lateinit var userManager: UserManager
    private lateinit var userViewModel: UserViewModel

     var id = ""
     var image = ""
     var password = ""
     var username = ""
     var name = ""
     var address = ""
     var age = ""

    //kurang image simpan ke datastore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        userManager = UserManager(this)
        userManager.userImage.asLiveData().observe(this){
            if (it !==""){
                Glide.with(this).load(it).into(iv_profile_image)
            }

        }
        initUM()
        logout()
        update()

        iv_profile_image.setOnClickListener {
            updateAvatar()
        }

    }


    fun logout(){
        btn_logout.setOnClickListener {
            GlobalScope.launch {
                userManager.clearData()

            }
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    fun initUM(){


        userManager.userPassword.asLiveData().observe(this, {
            this@ProfileActivity.password = it.toString()
        })

        userManager.userUsername.asLiveData().observe(this, {
            this@ProfileActivity.username = it.toString()
            et_update_username.setText(username)
        })

        userManager.userNama.asLiveData().observe(this, {
            this@ProfileActivity.name = it.toString()
            if(name!!.length > 0 && name!!.isNotEmpty()){
                et_update_name.setText(name)
            }
        })

        userManager.userAddress.asLiveData().observe(this, {
            this@ProfileActivity.address = it.toString()
            if(address!!.length > 0 && address!!.isNotEmpty()){
                et_update_address.setText(address)
            }
        })

        userManager.userUmur.asLiveData().observe(this, {
            this@ProfileActivity.age = it.toString()
            if(age!!.length > 0 && age!!.isNotEmpty()){
                et_update_umur.setText(age)
            }
        })

//        Toast.makeText(this, "ini $username", Toast.LENGTH_SHORT).show()

    }


    fun update(){
        btn_update_profile.setOnClickListener {


            if (et_update_address.text.isNotEmpty()
                && et_update_umur.text.isNotEmpty()
                && et_update_username.text.isNotEmpty()
                && et_update_name.text.isNotEmpty()){

                address = et_update_address.text.toString()
                age = et_update_umur.text.toString()
                username = et_update_username.text.toString()
                name = et_update_name.text.toString()
                image =  "https://ecs7.tokopedia.net/img/og_image_default_new.jpg"

                if(et_update_password.text.toString().isNotEmpty()){
                    password = et_update_password.text.toString()
                }


                userManager.userId.asLiveData().observe(this, {
                    this@ProfileActivity.id = it.toString()

                })

                AlertDialog.Builder(this)
                    .setTitle("Update data")
                    .setMessage("Yakin ingin mengupdate data?")
                    .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
                        userViewModel.updateUser(id.toInt(), username, password, name, address, age.toInt(), image)
                        Toast.makeText(this@ProfileActivity, "Update berhasil", Toast.LENGTH_LONG).show()
                        GlobalScope.launch {
                            userManager.saveData(
                                id,
                                username,
                                password,
                                name,
                                age,
                                image,
                                address
                            )
                        }
                        startActivity(Intent(this, HomeActivity::class.java))

                    }.show()


            }else{
                Toast.makeText(this@ProfileActivity, "Data belum lengkap", Toast.LENGTH_LONG).show()
            }

        }
    }


    override fun onResume() {
        super.onResume()
        initUM()
    }

    override fun onDestroy() {
        super.onDestroy()
        initUM()

    }


    private fun updateAvatar() {
        val avatarIntent = Intent(Intent.ACTION_GET_CONTENT)
        avatarIntent.type = "image/*"
        if (avatarIntent.resolveActivity(this.packageManager) != null) {
            startActivityForResult(avatarIntent, 2000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 2000 && data != null){
            iv_profile_image.setImageURI(data?.data)
            //save image string ke datastore
            GlobalScope.launch {
//                userManager.saveDataImage(data?.data.toString())
            }
        }
    }

}