package com.example.challengechapterenam.view.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentDetailBinding
import com.example.challengechapterenam.dataclass.FavoriteFilm
import com.example.challengechapterenam.datastore.UserLoginManager
import com.example.challengechapterenam.model.GetAllFilmResponseItem
import com.example.challengechapterenam.roomdatabase.FavoriteFilmDatabase
import com.example.challengechapterenam.roomdatabase.FavoriteFilmRepository
import com.example.challengechapterenam.viewmodel.ViewModelFactoryFavoriteFilm
import com.example.challengechapterenam.viewmodel.ViewModelFavoriteFilm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var fragmentDetailBinding: FragmentDetailBinding? = null
    private lateinit var favoriteFilmDatabase: FavoriteFilmDatabase
    private lateinit var favoriteFilmRepository: FavoriteFilmRepository
    private lateinit var factory: ViewModelFactoryFavoriteFilm
    private lateinit var favoriteFilmViewModel: ViewModelFavoriteFilm
    private lateinit var username: String
    private lateinit var userLoginManager: UserLoginManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        fragmentDetailBinding = binding

        favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(requireContext())!!
        favoriteFilmRepository = FavoriteFilmRepository(favoriteFilmDatabase)
        factory = ViewModelFactoryFavoriteFilm(favoriteFilmRepository)
        favoriteFilmViewModel = ViewModelProvider(this, factory)[ViewModelFavoriteFilm::class.java]

        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner) {
            username = it.toString()
        }
        getAllDetails()
    }

    @SuppressLint("SetTextI18n")
    private fun getAllDetails() {
        if(requireArguments().containsKey("FILMDATA")){
            val filmDetail = arguments?.getSerializable("FILMDATA") as GetAllFilmResponseItem
            fragmentDetailBinding!!.detailTitle.text = "Judul : \n${filmDetail.name}"
            fragmentDetailBinding!!.detailDirector.text = "Sutradara : \n${filmDetail.director}"
            fragmentDetailBinding!!.detailReleaseDate.text = "Tanggal rilis : \n${filmDetail.date}"
            fragmentDetailBinding!!.detailDescription.text = "Deskripsi : \n${filmDetail.description}"
            Glide.with(fragmentDetailBinding!!.detailImage.context)
                .load(filmDetail.image)
                .error(R.drawable.ic_launcher_background)
                .override(100, 100)
                .into(fragmentDetailBinding!!.detailImage)

            fragmentDetailBinding!!.detailAddToFavorite.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("Tambah ke favorit")
                    .setMessage("Anda yakin ingin menambahkan film ke list favorit?")
                    .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA"){ _: DialogInterface, _ : Int ->
                        insertNewFavoriteFilm(
                            FavoriteFilm(
                                null,
                                username,
                                filmDetail.date,
                                filmDetail.description,
                                filmDetail.director,
                                filmDetail.id,
                                filmDetail.image,
                                filmDetail.name
                            )
                        )
                    }.show()
            }
        }else if(requireArguments().containsKey("FAVORITEFILMDATA")){
            val filmDetail = arguments?.getParcelable<FavoriteFilm>("FAVORITEFILMDATA")
            fragmentDetailBinding!!.detailTitle.text = "Judul : \n${filmDetail!!.name}"
            fragmentDetailBinding!!.detailDirector.text = "Sutradara : \n${filmDetail.director}"
            fragmentDetailBinding!!.detailReleaseDate.text = "Tanggal rilis : \n${filmDetail.date}"
            fragmentDetailBinding!!.detailDescription.text = "Deskripsi : \n${filmDetail.description}"
            Glide.with(fragmentDetailBinding!!.detailImage.context)
                .load(filmDetail.image)
                .error(R.drawable.ic_launcher_background)
                .override(100, 100)
                .into(fragmentDetailBinding!!.detailImage)
            fragmentDetailBinding!!.detailAddToFavorite.setOnClickListener {
                AlertDialog.Builder(requireContext())
                    .setTitle("HAPUS FILM DARI LIST FAVORIT")
                    .setMessage("Anda yakin ingin menghapus film ini dari list favorit?")
                    .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                        dialogInterface.dismiss()
                    }
                    .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                        deleteFavoriteFilmById(filmDetail.id!!)
                    }.show()
            }
        }
    }
    private fun insertNewFavoriteFilm(favoriteFilm: FavoriteFilm) {
        CoroutineScope(Dispatchers.Main).launch {
            favoriteFilmViewModel.insertFavoriteFilm(favoriteFilm).also {
                Toast.makeText(requireContext(), "Berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            }
        }
    }
//    private fun deleteFavoriteFilmFromList(favoriteFilm: FavoriteFilm){
//        CoroutineScope(Dispatchers.Main).launch {
//            favoriteFilmViewModel.deleteFavoriteFilm(favoriteFilm).also {
//                Toast.makeText(requireContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    private fun deleteFavoriteFilmById(id : Int) {
        CoroutineScope(Dispatchers.Main).launch {
            favoriteFilmViewModel.deleteFavoriteFilmById(id).also {
                Toast.makeText(requireContext(), "Berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }
}