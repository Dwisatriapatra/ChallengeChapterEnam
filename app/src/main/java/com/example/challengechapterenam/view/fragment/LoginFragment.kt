package com.example.challengechapterenam.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentLoginBinding
import com.example.challengechapterenam.datastore.UserLoginManager
import com.example.challengechapterenam.model.GetAllUserResponseItem
import com.example.challengechapterenam.networking.ApiUserServices
import com.example.challengechapterenam.repository.UserRepository
import com.example.challengechapterenam.viewmodel.ViewModelFactoryUser
import com.example.challengechapterenam.viewmodel.ViewModelUserApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var fragmentLoginBinding : FragmentLoginBinding? = null
    private lateinit var viewModelUserApi : ViewModelUserApi
    private lateinit var userLoginManager : UserLoginManager

    private val apiUserServices = ApiUserServices.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentLoginBinding.bind(view)
        fragmentLoginBinding = binding

        fragmentLoginBinding!!.buttonLogin.setOnClickListener {
            initUserApiViewModel()
        }

        fragmentLoginBinding!!.loginRegisteringAccount.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun initUserApiViewModel() {
        viewModelUserApi = ViewModelProvider(
            this, ViewModelFactoryUser(UserRepository(apiUserServices))
        ).get(ViewModelUserApi::class.java)
        viewModelUserApi.liveDataUserApi.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                loginAuth(it)
            }
        }
        viewModelUserApi.getAllUser()
    }

    private fun loginAuth(list: List<GetAllUserResponseItem>) {
        userLoginManager = UserLoginManager(requireContext())

        //inputan user
        val inputanEmail = fragmentLoginBinding!!.loginInputEmail.text.toString()
        val inputanPassword = fragmentLoginBinding!!.loginInputPassword.text.toString()

        if(inputanEmail.isNotEmpty() && inputanPassword.isNotEmpty()){
            for(i in list.indices){
                if(inputanEmail == list[i].email && inputanPassword == list[i].password){
                    Toast.makeText(requireContext(), "Berhasil login", Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        userLoginManager.setBoolean(true)
                        userLoginManager.saveDataLogin(
                            list[i].alamat,
                            list[i].email,
                            list[i].id,
                            list[i].image,
                            list[i].name,
                            list[i].password,
                            list[i].tanggalLahir,
                            list[i].username
                        )
                    }
                    Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
                }
            }
        }else{
            Toast.makeText(requireContext(), "Semua field harus diisi", Toast.LENGTH_SHORT).show()
        }
    }
}