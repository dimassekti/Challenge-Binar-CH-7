package com.coufie.challengechapterseven.datastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {


    private val dataStore: DataStore<Preferences> = context.createDataStore("user_login")

    suspend fun saveData(id:String, username:String, password:String, nama:String, umur:String, image:String, address:String){
        dataStore.edit {
            it[Id] = id
            it[Username] = username
            it[Password] = password
            it[Nama] = nama
            it[Umur] = umur
            it[Image] = image
            it[Address] = address

        }
    }

    suspend fun clearData(){
        dataStore.edit {
            it.clear()
        }
    }

    val userUsername : Flow<String> = dataStore.data.map {
        it[Username] ?: ""
    }

    val userPassword : Flow<String> = dataStore.data.map {
        it[Password] ?: ""
    }

    val userNama : Flow<String> = dataStore.data.map {
        it[Nama] ?: ""
    }

    val userUmur : Flow<String> = dataStore.data.map {
        it[Umur] ?: ""
    }

    val userImage : Flow<String> = dataStore.data.map {
        it[Image] ?: ""
    }

    val userAddress : Flow<String> = dataStore.data.map {
        it[Address] ?: ""
    }

    val userId : Flow<String> = dataStore.data.map {
        it[Id] ?: ""
    }

    companion object{
        val Id = preferencesKey<String>("user_id")
        val Username = preferencesKey<String>("user_username")
        val Password = preferencesKey<String>("user_password")
        val Nama = preferencesKey<String>("user_nama")
        val Umur = preferencesKey<String>("user_umur")
        val Image = preferencesKey<String>("user_image")
        val Address = preferencesKey<String>("user_address")


    }


}