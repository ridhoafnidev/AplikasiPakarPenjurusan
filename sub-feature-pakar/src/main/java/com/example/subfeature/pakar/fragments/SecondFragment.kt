package com.example.subfeature.pakar.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.R
import com.example.subfeature.pakar.databinding.FragmentFirstBinding
import com.example.subfeature.pakar.databinding.FragmentSecondBinding
import com.example.subfeature.pakar.viewholder.QuestionViewHolder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding

    private val viewModel: PakarSecondViewModel by sharedViewModel()

    private val data by lazy {
        listOf(
            Questions("B01", "Matematika"),
            Questions("B02", "Fisika"),
            Questions("B03","Biologi"),
            Questions("B04", "Kimia"),
            Questions("B05", "Bahasa Asing"),
            Questions("B06", "Bahasa dan Sastra Inggris")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        with(binding){
            //tvTitle.text = onBoardingItem.title
            rvSecond.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(data))
                withItem<Questions, QuestionViewHolder>(R.layout.layout_question){
                    onBind(::QuestionViewHolder){ index, data ->
                        titleCheckBox.text = data.question
                        titleCheckBox.setOnCheckedChangeListener { _, isChecked ->
                            viewModel.putFormDataValue(data.id, if(isChecked) "1" else "0")
                        }
                    }
                }
            }
        }
    }
}