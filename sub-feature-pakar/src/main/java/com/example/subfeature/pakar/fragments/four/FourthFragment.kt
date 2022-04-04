package com.example.subfeature.pakar.fragments.four

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
import com.example.subfeature.pakar.databinding.FragmentFourthBinding
import com.example.subfeature.pakar.goToFifthPakar
import com.example.subfeature.pakar.viewholder.QuestionViewHolder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FourthFragment : Fragment() {

    private lateinit var binding: FragmentFourthBinding

    private val viewModel: PakarFourthViewModel by sharedViewModel()

    private val data by lazy {
        listOf(
            Questions("M01", "Matematika"),
            Questions("M02", "Fisika"),
            Questions("M03","Kimia"),
            Questions("M04", "Biologi"),
            Questions("M05", "Geografi"),
            Questions("M06", "Ekonomi"),
            Questions("M07", "Sejarah"),
            Questions("M08", "Sosiologi"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFourthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupInput()
    }

    private fun setupInput() {
        with(binding){
            next.setOnClickListener {
                goToFifthPakar()
            }
            preview.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

    private fun setupData() {
        with(binding){
            rvFourth.setup {
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