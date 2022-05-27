package com.coufie.challengechapterseven.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.view.adapter.AdapterFilmFavourite
import com.coufie.challengechapterseven.room.FilmDatabase
import com.coufie.challengechapterseven.view.detail_page.DetailFilm
import kotlinx.android.synthetic.main.activity_favourite.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {

    var filmDb : FilmDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite)


        filmDb = FilmDatabase.getInstance(this)
        getFilmFav()

    }

    fun getFilmFav(){

        rv_favfilm.layoutManager = LinearLayoutManager(this)

        GlobalScope.launch {
            val listFavFilm = filmDb?.filmDao()?.getFilmFavourite()
            runOnUiThread{
                if(listFavFilm?.size!! < 1){
                    tv.setText("Favourite-ku masing kosong")
                }else{
                    listFavFilm.let {
                        rv_favfilm.adapter = AdapterFilmFavourite(it!!){
                            val pindah = Intent(this@FavouriteActivity, DetailFilm::class.java )
                            pindah.putExtra("DETAILFILMFAV", it)
                            startActivity(pindah)

                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getFilmFav()
    }

    override fun onDestroy() {
        super.onDestroy()
        getFilmFav()
    }

}