package com.example.challengechapterenam.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentRegisterBinding
import com.example.challengechapterenam.networking.ApiUserServices
import com.example.challengechapterenam.repository.UserRepository
import com.example.challengechapterenam.viewmodel.ViewModelFactoryUser
import com.example.challengechapterenam.viewmodel.ViewModelUserApi

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var fragmentRegisterBinding: FragmentRegisterBinding? = null
    private lateinit var viewModelUserApi : ViewModelUserApi

    private val apiUserServices = ApiUserServices.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        fragmentRegisterBinding = binding

        //register button action
        fragmentRegisterBinding!!.buttonRegister.setOnClickListener {
            tambahUser()
        }
    }

    //get all user input, then call postUser function
    private fun tambahUser(){
        val nama = fragmentRegisterBinding!!.registerInputNama.text.toString()
        val username = fragmentRegisterBinding!!.registerInputUsername.text.toString()
        val alamat = fragmentRegisterBinding!!.registerInputAlamat.text.toString()
        val tanggalLahir = fragmentRegisterBinding!!.registerInputTanggalLahir.text.toString()
        val image = "http://placeimg.com/640/480/city"
        val password = fragmentRegisterBinding!!.registerInputPassword.text.toString()
        val email = fragmentRegisterBinding!!.registerInputEmail.text.toString()
        val konfirmasiPassword = fragmentRegisterBinding!!.registerInputKonfirmasiPassword.text.toString()

        //check if all fields is not empty
        if (nama.isNotEmpty() && username.isNotEmpty() && alamat.isNotEmpty() &&
            tanggalLahir.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty() &&
            konfirmasiPassword.isNotEmpty()
        ) {
            //check similarity of password and konfirmasiPassword
            if (password == konfirmasiPassword) {
                postUser(alamat, email, image, username, tanggalLahir, password, nama)
            } else {
                Toast.makeText(requireContext(),"Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        }
    }

    //post user is function to use addNewUser function that provided by view model
    private fun postUser(
        alamat: String,
        email: String,
        image: String,
        username: String,
        tanggalLahir: String,
        password: String,
        name: String
    ) {
        viewModelUserApi = ViewModelProvider(
            viewModelStore, ViewModelFactoryUser(UserRepository(apiUserServices))
        ).get(ViewModelUserApi::class.java)
        viewModelUserApi.addNewUser(alamat, email, image, username, tanggalLahir, password, name)

        // toast
        Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
    }
}