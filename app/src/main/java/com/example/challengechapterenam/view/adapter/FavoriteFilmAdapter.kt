package com.example.challengechapterenam.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengechapterenam.databinding.ItemFilmAdapterBinding
import com.example.challengechapterenam.dataclass.FavoriteFilm
//adapter class for favorite film list
class FavoriteFilmAdapter(private val onClick : (FavoriteFilm) -> Unit) :
RecyclerView.Adapter<FavoriteFilmAdapter.ViewHolder>(){

    private var listFavoriteFilm : List<FavoriteFilm>? = null
    fun setDataFavoriteFilm(list : List<FavoriteFilm>) {
        this.listFavoriteFilm = list
    }

    inner class ViewHolder(val binding : ItemFilmAdapterBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteFilmAdapter.ViewHolder {
        val binding = ItemFilmAdapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FavoriteFilmAdapter.ViewHolder, position: Int) {
        with(holder){
            with(listFavoriteFilm!![position]){
                binding.filmReleaseDate.text = "Tanggal rilis : \n$date"
                binding.filmDirector.text = "Sutradara : \n$director"
                binding.filmTitle.text = "Judul film : \n$name"
                Glide.with(binding.filmImage.context)
                    .load(image)
                    .error(com.example.challengechapterenam.R.drawable.ic_launcher_background)
                    .override(50, 100)
                    .into(binding.filmImage)
            }
        }
        holder.binding.buttonSeeDetail.setOnClickListener {
            onClick(listFavoriteFilm!![position])
        }
    }

    override fun getItemCount(): Int {
        return if(listFavoriteFilm.isNullOrEmpty()){
            0
        }else{
            listFavoriteFilm!!.size
        }
    }

}