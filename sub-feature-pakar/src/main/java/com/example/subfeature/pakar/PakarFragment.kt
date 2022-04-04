package com.example.subfeature.pakar

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.example.core_data.domain.Answer
import com.example.core_resource.components.base.BaseFragment
import com.example.subfeature.pakar.adapter.ViewPagerAdapter
import com.example.subfeature.pakar.databinding.FragmentPakarBinding
import com.example.subfeature.pakar.fragments.one.PakarFirstViewModel
import com.example.subfeature.pakar.fragments.two.PakarSecondViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PakarFragment : BaseFragment<FragmentPakarBinding>(FragmentPakarBinding::inflate) {

    private val pakarViewModel: PakarViewModel by sharedViewModel()
    private val firstPakarViewModel: PakarFirstViewModel by sharedViewModel()
    private val secondPakarViewModel: PakarSecondViewModel by sharedViewModel()

    private val itemViewPager2 by lazy {
        binding.viewPager
    }

    private val indicatorContainer by lazy {
        binding.indicatorContainer
    }

    private val btnNext by lazy {
        binding.next
    }

    private val btnPrev by lazy {
        binding.preview
    }

    private val itemAdapter by lazy {
        ViewPagerAdapter(requireActivity().supportFragmentManager, firstPakarViewModel)
    }

    override fun initView(){
        setupIndicators()
        setupCurrentIndicator(0)
        itemViewPager2.adapter = itemAdapter
        itemViewPager2.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                setupCurrentIndicator(position)
            }

            override fun onPageSelected(position: Int) {
                setupCurrentIndicator(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    private fun setupIndicators() {
        val indicator = arrayOfNulls<ImageView>(itemAdapter.count)
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(8,0, 8,0)
        for(i in indicator.indices){
            indicator[i] = ImageView(requireContext())
            indicator[i]?.let { imageView ->
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background
                    )
                )
                imageView.layoutParams = layoutParams
                indicatorContainer.addView(imageView)
            }
        }
    }

    private fun setupCurrentIndicator(position: Int) {
        val childCount = indicatorContainer.childCount

        if (position == childCount - 1) btnNext.text = "SELESAI"
        else {
            btnNext.text = "NEXT"
            btnNext.visibility = View.VISIBLE
        }

        if (position == 0) btnPrev.visibility = View.INVISIBLE
        else btnPrev.visibility = View.VISIBLE

        for (i in 0 until childCount){
            val imageView = indicatorContainer.getChildAt(i) as ImageView
            if(i == position){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_active_background
                    )
                )
            }
            else{
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.indicator_inactive_background
                    )
                )
            }
        }

    }

    override fun initListener() {
        with(binding){
            val data = arrayListOf<Answer>()
            next.setOnClickListener {
                if (itemViewPager2.currentItem + 1 < itemAdapter.count){
                    itemViewPager2.currentItem += 1
                }
                else {
                    if (next.text.equals("SELESAI")){
                        data.addAll(firstPakarViewModel.getFinalData())
                        data.addAll(secondPakarViewModel.getFinalData())
                        pakarViewModel.saveQuestionAnswer(1L, data)
                    }
                }
            }
            preview.setOnClickListener {
                if (itemViewPager2.currentItem -1 < itemAdapter.count){
                    itemViewPager2.currentItem -= 1
                }
            }
        }
    }

}