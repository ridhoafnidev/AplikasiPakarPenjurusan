package com.example.subfeature.pakar

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.core_data.domain.OnBoardingData
import com.example.core_data.domain.Questions
import com.example.core_resource.components.base.BaseFragment
import com.example.subfeature.pakar.adapter.OnBoardingItemAdapter
import com.example.subfeature.pakar.databinding.FragmentPakarBinding

class PakarFragment : BaseFragment<FragmentPakarBinding>(FragmentPakarBinding::inflate) {

    private val itemsAdapter by lazy {
        OnBoardingItemAdapter(
            listOf(
                OnBoardingData(
                    "Title1",
                    "Desc1",
                    listOf(Questions("A"), Questions("B"), Questions("C"))
                ),
                OnBoardingData(
                    "Title2",
                    "Desc2",
                    listOf(Questions("D"), Questions("E"), Questions("F"))
                ),
                OnBoardingData(
                    "Title3",
                    "Desc3",
                    listOf(Questions("G"), Questions("H"), Questions("I"))
                ),
                OnBoardingData(
                    "Title4",
                    "Desc4",
                    listOf(Questions("H"), Questions("I"), Questions("J"))
                )
            )
        )
    }

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


    override fun initView(){
        setupIndicators()
        setupCurrentIndicator(0)
        itemViewPager2.adapter = itemsAdapter
        itemViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setupCurrentIndicator(position)
            }
        })
    }

    private fun setupIndicators() {
        val indicator = arrayOfNulls<ImageView>(itemsAdapter.itemCount)
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
            next.setOnClickListener {
                if (itemViewPager2.currentItem + 1 < itemsAdapter.itemCount){
                    itemViewPager2.currentItem += 1
                }
            }
            preview.setOnClickListener {
                if (itemViewPager2.currentItem -1 < itemsAdapter.itemCount){
                    itemViewPager2.currentItem -= 1
                }
            }
        }
    }

}