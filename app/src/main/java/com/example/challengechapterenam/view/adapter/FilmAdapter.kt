package com.example.challengechapterenam.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.ItemFilmAdapterBinding
import com.example.challengechapterenam.model.GetAllFilmResponseItem

class FilmAdapter(
    private val onClick : (GetAllFilmResponseItem) -> Unit
) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    private var listFilm :List<GetAllFilmResponseItem>? = null

    fun setDataFilm(list : List<GetAllFilmResponseItem>){
        this.listFilm = list
    }


    inner class ViewHolder(val binding : ItemFilmAdapterBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.ViewHolder {
        val binding = ItemFilmAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmAdapter.ViewHolder, position: Int) {
        with(holder){
            with(listFilm!![position]){
                binding.filmReleaseDate.text = "Tanggal rilis : \n$date"
                binding.filmDirector.text = "Sutradara : \n$director"
                binding.filmTitle.text = "Judul film : \n$name"
                Glide.with(binding.filmImage.context)
                    .load(image)
                    .error(R.drawable.ic_launcher_background)
                    .override(50, 100)
                    .into(binding.filmImage)
            }
        }
        holder.binding.buttonSeeDetail.setOnClickListener {
            onClick(listFilm!![position])
        }
    }

    override fun getItemCount(): Int {
        return if(listFilm.isNullOrEmpty()){
            0
        }else{
            listFilm!!.size
        }
    }
}