package com.coufie.challengechapterseven.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Film::class], version = 2)
abstract class FilmDatabase : RoomDatabase(){
    abstract fun filmDao() : FilmDao

    companion object{
        private var INSTANCE : FilmDatabase? = null
        fun getInstance(context : Context): FilmDatabase? {
            if (INSTANCE == null){
                synchronized(FilmDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FilmDatabase::class.java,"filmfav.db").allowMainThreadQueries().fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}