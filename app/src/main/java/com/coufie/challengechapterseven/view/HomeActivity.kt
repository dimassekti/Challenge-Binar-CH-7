package com.coufie.challengechapterseven.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.datastore.UserManager
import com.coufie.challengechapterseven.view.adapter.FilmAdapter
import com.coufie.challengechapterseven.view.detail_page.DetailFilm
import com.coufie.challengechapterseven.viewmodel.FilmViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.btn_logout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var filmAdapter: FilmAdapter
    lateinit var userManager : UserManager
    lateinit var id : String
    lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        userManager = UserManager(this)

        userManager.userUsername.asLiveData().observe(this, {
            this@HomeActivity.username = it.toString()
            introx.setText(username)
        })


        logout()
        favourites()
        initFilm()
        runOnUiThread{

        }
        profile()

    }

    fun favourites(){
        iv_favourites.setOnClickListener {
            startActivity(Intent(this, FavouriteActivity::class.java))
        }
    }

    fun profile(){
        iv_profile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
//            userManager.userId.asLiveData().observe(this, {
//                id = it.toString()
//                Toast.makeText(this, "ini $id", Toast.LENGTH_SHORT).show()
//            })
        }
    }

    fun logout(){
        btn_logout.setOnClickListener {
            GlobalScope.launch {
                userManager.clearData()

            }
            startActivity(Intent(this@HomeActivity, LoginActivity::class.java))
        }
    }

    fun initFilm(){
        val filmadapter = FilmAdapter(){
            val pindah = Intent(this, DetailFilm :: class.java)
            pindah.putExtra("DETAILFILM", it)
            startActivity(pindah)
        }

        rv_film.layoutManager = LinearLayoutManager(this@HomeActivity)
        rv_film.adapter = filmadapter

        val viewmodell = ViewModelProvider(this@HomeActivity).get(FilmViewModel::class.java)
        viewmodell.film.observe(this@HomeActivity, {
            if(it!=null){
                filmadapter.setFilmList(it)
                filmadapter.notifyDataSetChanged()
            }

        })
    }


}