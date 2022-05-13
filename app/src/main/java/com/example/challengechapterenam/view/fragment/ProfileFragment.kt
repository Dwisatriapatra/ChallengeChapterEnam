package com.example.challengechapterenam.view.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.bumptech.glide.Glide
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentProfileBinding
import com.example.challengechapterenam.datastore.UserLoginManager
import com.example.challengechapterenam.networking.ApiUserServices
import com.example.challengechapterenam.repository.UserRepository
import com.example.challengechapterenam.viewmodel.ViewModelFactoryUser
import com.example.challengechapterenam.viewmodel.ViewModelUserApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private var fragmentProfileBinding : FragmentProfileBinding? = null
    private lateinit var userLoginManager: UserLoginManager
    private lateinit var viewModelUser : ViewModelUserApi

    private var apiUserInterface = ApiUserServices.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProfileBinding.bind(view)
        fragmentProfileBinding = binding

        initField()
        fragmentProfileBinding!!.profileButtonLogout.setOnClickListener {
            logout()
        }
        fragmentProfileBinding!!.profileButtonUpdate.setOnClickListener {
            updateData()
        }
    }

    //init original data to the profile page
    private fun initField() {
        userLoginManager = UserLoginManager(requireContext())
        userLoginManager.image.asLiveData().observe(viewLifecycleOwner){
            Glide.with(fragmentProfileBinding!!.profileImage.context)
                .load(it)
                .error(R.drawable.profile_photo)
                .override(100, 100)
                .into(fragmentProfileBinding!!.profileImage)
        }
        userLoginManager.name.asLiveData().observe(viewLifecycleOwner){
            fragmentProfileBinding!!.profileNamaLengkap.setText(it.toString())
        }
        userLoginManager.dateOfBirth.asLiveData().observe(viewLifecycleOwner){
            fragmentProfileBinding!!.profileTanggalLahir.setText(it.toString())
        }
        userLoginManager.address.asLiveData().observe(viewLifecycleOwner){
            fragmentProfileBinding!!.profileAlamat.setText(it.toString())
        }
        userLoginManager.email.asLiveData().observe(viewLifecycleOwner){
            fragmentProfileBinding!!.profileEmail.setText(it.toString())
        }
        userLoginManager.username.asLiveData().observe(viewLifecycleOwner){
            fragmentProfileBinding!!.profileUsername.setText(it.toString())
        }
        userLoginManager.password.asLiveData().observe(viewLifecycleOwner){
            fragmentProfileBinding!!.profilePassword.setText(it.toString())
            fragmentProfileBinding!!.profileKonfirmasiPassword.setText(it.toString())
        }
    }

    //log out function, will erase user data that stored in data store
    private fun logout() {
        userLoginManager = UserLoginManager(requireContext())
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Apakah anda yakin ingin logout?")
            .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                GlobalScope.launch {
                    userLoginManager.clearDataLogin()
                }
                val mIntent =activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }

    //function to update data, will call function updateUser that provided by view model
    private fun updateData() {
        userLoginManager = UserLoginManager(requireContext())

        var id = ""
        val alamat = fragmentProfileBinding!!.profileAlamat.text.toString()
        val email = fragmentProfileBinding!!.profileEmail.text.toString()
        val image = "http://placeimg.com/640/480/city"
        val username = fragmentProfileBinding!!.profileUsername.text.toString()
        val tanggalLahir = fragmentProfileBinding!!.profileTanggalLahir.text.toString()
        val password = fragmentProfileBinding!!.profilePassword.text.toString()
        val namaLengkap = fragmentProfileBinding!!.profileNamaLengkap.text.toString()
        //get id for current user
        userLoginManager.IDuser.asLiveData().observe(viewLifecycleOwner){
            id = it.toString()
        }
        AlertDialog.Builder(requireContext())
            .setTitle("Update data")
            .setMessage("Yakin ingin mengupdate data?")
            .setNegativeButton("TIDAK"){ dialogInterface : DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setPositiveButton("YA"){ _: DialogInterface, _: Int ->
                viewModelUser = ViewModelProvider(this,
                    ViewModelFactoryUser(UserRepository(apiUserInterface))
                ).get(ViewModelUserApi::class.java)

                viewModelUser.updateUser(id,
                    alamat,
                    email,
                    image,
                    username,
                    tanggalLahir,
                    password,
                    namaLengkap
                )

                //change previous data that stored in data store
                Toast.makeText(requireContext(), "Update data berhasil", Toast.LENGTH_SHORT).show()
                GlobalScope.launch {
                    userLoginManager.saveDataLogin(
                        alamat,
                        email,
                        id,
                        image,
                        namaLengkap,
                        password,
                        tanggalLahir,
                        username
                    )
                }
                //restart activity
                val mIntent = activity?.intent
                activity?.finish()
                startActivity(mIntent)
            }.show()
    }
}