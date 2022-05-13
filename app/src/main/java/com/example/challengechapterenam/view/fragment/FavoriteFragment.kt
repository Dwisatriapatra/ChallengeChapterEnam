package com.example.challengechapterenam.view.fragment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentFavoriteBinding
import com.example.challengechapterenam.datastore.UserLoginManager
import com.example.challengechapterenam.roomdatabase.FavoriteFilmDatabase
import com.example.challengechapterenam.repository.FavoriteFilmRepository
import com.example.challengechapterenam.view.adapter.FavoriteFilmAdapter
import com.example.challengechapterenam.viewmodel.ViewModelFactoryFavoriteFilm
import com.example.challengechapterenam.viewmodel.ViewModelFavoriteFilm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {
    private var fragmentFavoriteBinding: FragmentFavoriteBinding? = null
    private lateinit var favoriteFilmDatabase: FavoriteFilmDatabase
    private lateinit var favoriteFilmRepository: FavoriteFilmRepository
    private lateinit var factory: ViewModelFactoryFavoriteFilm
    private lateinit var favoriteFilmViewModel: ViewModelFavoriteFilm
    private lateinit var username: String
    private lateinit var userLoginManager: UserLoginManager
    private lateinit var favoriteFilmAdapter: FavoriteFilmAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteBinding.bind(view)
        fragmentFavoriteBinding = binding

        favoriteFilmDatabase = FavoriteFilmDatabase.getInstance(requireContext())!!
        favoriteFilmRepository = FavoriteFilmRepository(favoriteFilmDatabase)
        factory = ViewModelFactoryFavoriteFilm(favoriteFilmRepository)
        favoriteFilmViewModel = ViewModelProvider(this, factory)[ViewModelFavoriteFilm::class.java]

        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner) {
            username = it.toString()
        }

        initRecyclerView()
        getFavoriteFilmData()
    }

    //init recycler view of favorite film list
    private fun initRecyclerView() {
        fragmentFavoriteBinding!!.rvFavoriteFilm.layoutManager =
            LinearLayoutManager(requireContext())
        favoriteFilmAdapter = FavoriteFilmAdapter {
            val clickedFilm = bundleOf("FAVORITEFILMDATA" to it)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_favoriteFragment_to_detailFragment, clickedFilm)
        }
        fragmentFavoriteBinding!!.rvFavoriteFilm.adapter = favoriteFilmAdapter
    }

    //this function will call getFavoriteFilm that provided by view model
    private fun getFavoriteFilmData() {
        CoroutineScope(Dispatchers.Main).launch {
            favoriteFilmViewModel.getFavoriteFilm().observe(viewLifecycleOwner) {
                if(it.isNotEmpty()){
                    favoriteFilmAdapter.setDataFavoriteFilm(it)
                    favoriteFilmAdapter.notifyDataSetChanged()
                }else{
                    fragmentFavoriteBinding!!.favoriteNoDataAnimation.setAnimation(R.raw.no_favorite_film_data)
                }

            }
        }

    }
}