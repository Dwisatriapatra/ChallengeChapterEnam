package com.example.challengechapterenam.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentDetailBinding
import com.example.challengechapterenam.datastore.UserLoginManager
import com.example.challengechapterenam.model.GetAllFilmResponseItem
import java.io.Serializable

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private var fragmentDetailBinding : FragmentDetailBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)
        fragmentDetailBinding = binding

        getAllDetails()
    }

    private fun getAllDetails() {
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

    }
}