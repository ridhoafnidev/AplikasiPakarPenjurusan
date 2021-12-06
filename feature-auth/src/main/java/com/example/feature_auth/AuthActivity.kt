package com.example.feature_auth

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.feature_auth.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAuthBinding.inflate(layoutInflater)
    }

    private val navController by lazy { findNavController(R.id.activity_auth_nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
        setContentView(binding.root)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}