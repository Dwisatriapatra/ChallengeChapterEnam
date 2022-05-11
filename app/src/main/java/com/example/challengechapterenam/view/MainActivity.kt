package com.example.challengechapterenam.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.challengechapterenam.R
import com.example.challengechapterenam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}