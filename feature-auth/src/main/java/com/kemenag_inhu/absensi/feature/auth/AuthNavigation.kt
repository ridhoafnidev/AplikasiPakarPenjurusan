package com.kemenag_inhu.absensi.feature.auth

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kemenag_inhu.absensi.feature_auth.R

fun Fragment.goToLogin() = findNavController().navigate(R.id.loginFragment)