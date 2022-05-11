package com.example.challengechapterenam.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentRegisterBinding
import com.example.challengechapterenam.model.PostNewUser
import com.example.challengechapterenam.model.RequestUser
import com.example.challengechapterenam.networking.ApiUserClient
import com.example.challengechapterenam.viewmodel.ViewModelUserApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var fragmentRegisterBinding: FragmentRegisterBinding? = null
    private lateinit var viewModelUserApi : ViewModelUserApi

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegisterBinding.bind(view)
        fragmentRegisterBinding = binding

        //register button action
        fragmentRegisterBinding!!.buttonRegister.setOnClickListener {
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
                    tambahUser(alamat, email, image, username, tanggalLahir, password, nama)
                } else {
                    Toast.makeText(requireContext(),"Password dan konfirmasi password harus sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun tambahUser(
        alamat: String,
        email: String,
        image: String,
        username: String,
        tanggalLahir: String,
        password: String,
        name: String
    ) {
        viewModelUserApi = ViewModelProvider(this).get(ViewModelUserApi::class.java)
        viewModelUserApi.addNewUserApi(alamat, email, image, username, tanggalLahir, password, name)

        // disini toast sebenarnya gagal, tapi data tetap masuk ke api
        Toast.makeText(requireContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(requireView()).navigate(R.id.action_registerFragment_to_loginFragment)
    }
}