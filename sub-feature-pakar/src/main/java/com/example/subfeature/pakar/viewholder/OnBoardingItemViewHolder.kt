package com.example.subfeature.pakar.viewholder

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.example.core_data.domain.OnBoardingData
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.PakarViewModel
import com.example.subfeature.pakar.R
import com.example.subfeature.pakar.databinding.LayoutItemBoardingBinding

class OnBoardingItemViewHolder(val binding: LayoutItemBoardingBinding, val context: Context) : RecyclerView.ViewHolder(binding.root){

//    private val pakarViewModel: PakarViewModel by lazy {
//        PakarViewModel()
//    }

    fun bind(onBoardingItem: OnBoardingData){
        with(binding){
            tvTitle.text = onBoardingItem.title
            rvItem.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(onBoardingItem.questions))
                withItem<Questions, QuestionViewHolder>(R.layout.layout_question){
                    onBind(::QuestionViewHolder){ index, data ->
                        titleCheckBox.text = data.question
                        titleCheckBox.setOnCheckedChangeListener { _, isChecked ->
//                            pakarViewModel.putFormDataValue(
//                                data.id,
//                                if (isChecked) "1" else "0"
//                            )
                        }
                    }
                }
            }
        }
    }
}