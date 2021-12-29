package com.example.subfeature.pakar.viewholder

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.example.core_data.domain.OnBoardingData
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.R
import com.example.subfeature.pakar.databinding.LayoutItemBoardingBinding

class OnBoardingItemViewHolder(val binding: LayoutItemBoardingBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){

    fun bind(onBoardingItem: OnBoardingData){
        with(binding){
            tvTitle.text = onBoardingItem.title
            tvDesc.text = onBoardingItem.desc
            rvItem.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(onBoardingItem.questions))
                withItem<Questions, QuestionViewHolder>(R.layout.layout_question){
                    onBind(::QuestionViewHolder){ _, data ->
                        titleCheckBox.text = data.question
                    }
                }
            }
        }
    }
}