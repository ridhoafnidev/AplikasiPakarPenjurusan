package com.example.feature.home

import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.core_data.api.ApiEvent
import com.example.core_navigation.ModuleNavigator
import com.example.core_resource.components.base.BaseFragment
import com.example.core_util.gone
import com.example.core_util.visible
import com.example.feature.auth.AuthViewModel
import com.example.home.R
import com.example.home.databinding.FragmentHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ModuleNavigator {

    private val viewModel by sharedViewModel<AuthViewModel>()

    private var level = ""
    private var userId = ""

    private var dotscount: Int = 0
    private var dots: Array<ImageView?>? = null
    private var viewPagerAdapter: BannerViewPagerAdapter? = null
    private var timer: Timer? = null

    override fun initView() {
        observeAuth()
        initImage()
    }

    private fun initImage() {
        viewPagerAdapter = BannerViewPagerAdapter(requireActivity())
        binding.vpGaleri.adapter = viewPagerAdapter

        viewPagerAdapter.let { viewPagerAdapter ->
            if (viewPagerAdapter != null) {
                dotscount = viewPagerAdapter.count
            }
        }

        dots = arrayOfNulls(dotscount)

        val params = LinearLayoutCompat.LayoutParams(
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
            LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        )
        params.marginStart = 8
        params.marginEnd = 8
        params.setMargins(8, 0, 8, 0)

        if (dots != null) {
            for (i in dots!!.indices) {
                val img = ImageView(requireActivity())
                dots!![i] = img
                dots!![i]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireActivity(),
                        R.drawable.nonactive_dot
                    )
                )
                binding.sliderDots.addView(dots!![i], params)
            }

            dots!![0]?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.active_dot
                )
            )
        }

        binding.vpGaleri.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                if (dots != null) {
                    for (i in dots!!.indices) {
                        dots!![i]?.setImageDrawable(
                            ContextCompat.getDrawable(
                                requireActivity(),
                                R.drawable.nonactive_dot
                            )
                        )
                    }

                    dots!![position]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireActivity(),
                            R.drawable.active_dot
                        )
                    )
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

//        timer = Timer()
//        timer?.scheduleAtFixedRate(MyTimerTask(), 2000, 8000)
    }

    override fun initListener() {
        with(binding) {
            ivPakar.setOnClickListener {
                viewModel.getLastResult(userId.toInt())
                viewModel.lastResultAll.observe(viewLifecycleOwner) { hasilAngket ->
                    when (hasilAngket) {
                        is ApiEvent.OnSuccess -> {
                            hasilAngket.getData()?.let {
                                if (it.isNullOrEmpty()) navigateToPakarActivity()
                                else Toast.makeText(
                                    requireContext(),
                                    "Data sudah ada",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } ?: navigateToPakarActivity()
                        }
                    }
                }
            }
            ivHasilAngket.setOnClickListener {
                navigateToHasilAngketActivity()
            }

            ivNilaiSiswa.setOnClickListener {
                navigateToNilaiSiswaActivity(level = level)
            }
            ivHasilPenjurusan.setOnClickListener {
                navigateToNilaiPenjurusanActivity(level = level, userId = userId)
            }

        }
    }

    private fun observeAuth() {
        viewModel.auth.observe(viewLifecycleOwner) { data ->
            if (!data?.idUser.toString().isEmpty()) {
                level = data?.level.toString()
                userId = data?.idUser.toString()

                if (level == "siswa") {
                    binding.cvPakar.visible()
                    binding.cvNilaiSiswa.gone()
                    binding.cvHasilAngket.visible()
                    binding.cvHasilPenjurusan.visible()
                } else {
                    binding.cvPakar.gone()
                    binding.cvNilaiSiswa.visible()
                    binding.cvHasilAngket.visible()
                    binding.cvHasilPenjurusan.visible()
                }
            }
        }

    }

    inner class MyTimerTask : TimerTask() {
        override fun run() {
            CoroutineScope(Dispatchers.Main).launch {
                when (binding.vpGaleri.currentItem) {
                    0 -> binding.vpGaleri.currentItem = 1
                    1 -> binding.vpGaleri.currentItem = 2
                    2 -> binding.vpGaleri.currentItem = 3
                    3 -> binding.vpGaleri.currentItem = 4
                    4 -> binding.vpGaleri.currentItem = 5
                    5 -> binding.vpGaleri.currentItem = 6
                    6 -> binding.vpGaleri.currentItem = 7
                    else -> binding.vpGaleri.currentItem = 0
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dots = null
        viewPagerAdapter = null
        timer = null
    }
}