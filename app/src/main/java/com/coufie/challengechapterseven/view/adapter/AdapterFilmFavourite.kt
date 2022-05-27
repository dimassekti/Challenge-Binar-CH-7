package com.coufie.challengechapterseven.view.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coufie.challengechapterseven.R
import com.coufie.challengechapterseven.room.Film
import com.coufie.challengechapterseven.room.FilmDatabase
import com.coufie.challengechapterseven.view.FavouriteActivity
import kotlinx.android.synthetic.main.item_film_fav.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterFilmFavourite(val listFilmFavourite: List<Film>, val filmOnClick : (Film)->Unit) : RecyclerView.Adapter<AdapterFilmFavourite.ViewHolder>() {

    var filmfavDb : FilmDatabase? = null

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val viewItem = LayoutInflater.from(parent.context).inflate(R.layout.item_film_fav, parent, false)

        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.apply {
            rootView.setOnClickListener {
                filmOnClick(listFilmFavourite[position])
            }
        }

        this.let {
            Glide.with(holder.itemView.context).load(listFilmFavourite!![position].image).into(holder.itemView.iv_filmimage)
        }
        holder.itemView.tv_filmdirector.text = listFilmFavourite[position].director.toString()
        holder.itemView.tv_filmtitle.text = listFilmFavourite[position].name.toString()

        holder.itemView.btn_favdelete.setOnClickListener {
            filmfavDb = FilmDatabase.getInstance(it.context)

            val ADBuilder = AlertDialog.Builder(it.context)
                .setTitle("Hapus Data")
                .setMessage("Yakin Hapus?")
                .setPositiveButton("Ya"){ dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.async {

                        val deleteResult = filmfavDb?.filmDao()?.deleteFilm(listFilmFavourite[position])

                        (holder.itemView.context as FavouriteActivity).runOnUiThread {
                            if(deleteResult != null){
                                Toast.makeText(it.context, "Data Berhasil dihapus", Toast.LENGTH_LONG).show()
                                (holder.itemView.context as FavouriteActivity).recreate()
                            }else{
                                Toast.makeText(it.context, "Data Gagal dihapus", Toast.LENGTH_LONG).show()
                            }
                        }

                    }
                }
                .setNegativeButton("Tidak"){ dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .show()

        }
    }

    override fun getItemCount(): Int {
        return listFilmFavourite.size
    }


}