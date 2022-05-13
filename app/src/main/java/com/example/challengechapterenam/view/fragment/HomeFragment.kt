package com.example.challengechapterenam.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentHomeBinding
import com.example.challengechapterenam.datastore.UserLoginManager
import com.example.challengechapterenam.networking.ApiFilmServices
import com.example.challengechapterenam.repository.FilmApiRepository
import com.example.challengechapterenam.view.adapter.FilmAdapter
import com.example.challengechapterenam.viewmodel.ViewModelFactoryFilmApi
import com.example.challengechapterenam.viewmodel.ViewModelFilmApi

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private lateinit var filmViewModel: ViewModelFilmApi
    private lateinit var adapterFilm: FilmAdapter
    private lateinit var userLoginFragment: UserLoginManager
    private val apiFilmServices = ApiFilmServices.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding = binding

        initRecyclerView()
        getFilmDataViewModel()
        getUserProfile()

        fragmentHomeBinding!!.homeToProfile.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_profileFragment)
        }
        fragmentHomeBinding!!.homeToFavorite.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
    }

    private fun initRecyclerView() {
        fragmentHomeBinding!!.rvFilm.layoutManager = LinearLayoutManager(requireContext())
        adapterFilm = FilmAdapter {
            val clickedFilm = bundleOf("FILMDATA" to it)
            Navigation.findNavController(requireView())
                .navigate(R.id.action_homeFragment_to_detailFragment, clickedFilm)
        }
        fragmentHomeBinding!!.rvFilm.adapter = adapterFilm
    }

    private fun getFilmDataViewModel() {
        filmViewModel = ViewModelProvider(
            this, ViewModelFactoryFilmApi(FilmApiRepository(apiFilmServices))
        ).get(
            ViewModelFilmApi::class.java
        )

        filmViewModel.liveDataFilmApi.observe(viewLifecycleOwner){
            adapterFilm.setDataFilm(it)
        }
        filmViewModel.getAllFilmApi()
    }

    //function for get profile
    @SuppressLint("SetTextI18n")
    private fun getUserProfile() {
        userLoginFragment = UserLoginManager(requireContext())
        userLoginFragment.username.asLiveData().observe(viewLifecycleOwner) {
            fragmentHomeBinding!!.welcomeText.text = "Welcome, $it"
        }
    }
}