package com.example.challengechapterenam.view.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.navigation.Navigation
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.FragmentSplashBinding
import com.example.challengechapterenam.datastore.UserLoginManager

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var fragmentSplashBinding: FragmentSplashBinding? = null
    private lateinit var userLoginManager: UserLoginManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSplashBinding.bind(view)
        fragmentSplashBinding = binding

        userLoginManager = UserLoginManager(requireContext())
        Handler(Looper.getMainLooper()).postDelayed({
            //check if user was logging in or not
            userLoginManager.boolean.asLiveData().observe(viewLifecycleOwner) {
                if (it == true) {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_splashFragment_to_homeFragment)
                } else {
                    Navigation.findNavController(view)
                        .navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }, 4000)
    }
}