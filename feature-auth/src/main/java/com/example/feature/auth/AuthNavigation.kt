package com.example.feature.auth

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.feature_auth.R

fun Fragment.goToLogin() = findNavController().navigate(R.id.loginFragment)