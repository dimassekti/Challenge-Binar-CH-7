package com.coufie.challengechapterseven.room

import androidx.room.*

@Dao
interface FilmDao {

    @Insert
    fun insertFilm(film: Film)  : Long

    @Query("SELECT * FROM Film")
    fun getFilmFavourite() : List<Film>

    @Delete
    fun deleteFilm(film: Film) : Int


}