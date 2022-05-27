package com.coufie.challengechapterseven.view.detail_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.model.GetAllFilmItem
import com.coufie.challengechapterseven.room.Film
import com.coufie.challengechapterseven.room.FilmDatabase
import kotlinx.android.synthetic.main.activity_detail_film.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class DetailFilm : AppCompatActivity() {

    var filmfavdb : FilmDatabase? = null
    var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_film)

        filmfavdb = FilmDatabase.getInstance(this)

        detailFilm()
        addFilmFav()

    }

    fun detailFilm(){
        var detailFilm = intent.getParcelableExtra("DETAILFILM") as GetAllFilmItem?
        var detailFilmFav = intent.getParcelableExtra<Film>("DETAILFILMFAV")

        if(detailFilm!=null){

            tv_film_director.text = "Director : ${detailFilm!!.director}"
            tv_film_synopsis.text = detailFilm!!.description
            tv_film_title.text = detailFilm!!.name
            Glide.with(this).load(detailFilm!!.image).into(iv_film_cover)
        }else if (detailFilmFav!=null){

            tv_film_director.text = "Director : ${detailFilmFav!!.director}"
            tv_film_synopsis.text = detailFilmFav!!.description
            tv_film_title.text = detailFilmFav!!.name
            Glide.with(this).load(detailFilmFav!!.image).into(iv_film_cover)
        }
    }

    fun addFilmFav(){
        var detailFilm = intent.getParcelableExtra("DETAILFILM") as GetAllFilmItem?
        var detailFilmFav = intent.getParcelableExtra<Film>("DETAILFILMFAV")

        iv_addfavfilm.setOnClickListener{

            GlobalScope.async {

                var director = detailFilm!!.director
                var description = detailFilm!!.description
                var name = detailFilm!!.name
                var image = detailFilm!!.image
                id = detailFilm!!.id
                var date = detailFilm!!.date

                runOnUiThread {
                    var checkdata = filmfavdb?.filmDao()?.insertFilm(Film(id!!.toInt(), date, description, director, image, name) )
                    if(checkdata != 0.toLong()){
                        Toast.makeText(this@DetailFilm, "Film Ditambahkan Ke Favourite", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@DetailFilm, "gagal", Toast.LENGTH_SHORT).show()
                    }

                }

            }
        }
    }




}