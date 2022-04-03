package com.example.subfeature.pakar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.subfeature.pakar.fragments.one.FirstFragment
import com.example.subfeature.pakar.fragments.one.PakarFirstViewModel
import com.example.subfeature.pakar.fragments.two.SecondFragment
import com.example.subfeature.pakar.fragments.three.ThirdFragment

class ViewPagerAdapter(fm: FragmentManager, val firstViewModel: PakarFirstViewModel) : FragmentPagerAdapter(fm) {

    private val count = 2

    override fun getCount(): Int {
        return count
    }


    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FirstFragment()
            1 -> {
                if (firstViewModel.getFinalData().get(0).answer == "J01" ) SecondFragment() else ThirdFragment()
            }
            else -> FirstFragment()
        }
    }

}