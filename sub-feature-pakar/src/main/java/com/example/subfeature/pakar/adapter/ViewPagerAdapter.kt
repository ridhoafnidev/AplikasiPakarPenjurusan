package com.example.subfeature.pakar.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.subfeature.pakar.fragments.FirstFragment
import com.example.subfeature.pakar.fragments.SecondFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val count = 2

    override fun getCount(): Int {
        return count
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> FirstFragment()
            1 -> SecondFragment()
            else -> FirstFragment()
        }
    }

}