package com.example.challengechapterenam.dataclass

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
//favorite film data class
@Parcelize
@Entity(tableName = "favorite_film")
data class FavoriteFilm(
    @PrimaryKey(autoGenerate = true) val id :Int?,
    val username : String,
    val date : String,
    val description : String,
    val director : String,
    val idFilm : String,
    val image : String,
    val name : String
) : Parcelable