package com.example.subfeature.pakar.fragments.five

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.databinding.*
import com.example.subfeature.pakar.goToSeventhPakar
import com.example.subfeature.pakar.goToSixthPakar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FivethFragment : Fragment() {

    private lateinit var binding: FragmentFivethBinding

    private val viewModel: PakarFifthViewModel by sharedViewModel()

    private lateinit var layoutRadioGroup: RadioGroup

    private val data by lazy {
        listOf (
            Questions("P01", "Kesehatan"),
            Questions("P02", "Ekonomi"),
            Questions("P03","Pendidikan"),
            Questions("P04", "Aparatur Sipil Negara"),
            Questions("P05", "Media")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFivethBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
        setupInput()
    }

    private fun setupInput() {
        with(binding){

            layoutRadioGroup = radioGroup

            data.forEachIndexed { indexOption, option ->
                val radioButton = RadioButton(requireContext())
                radioButton.id = indexOption
                radioButton.text = option.question

                layoutRadioGroup.addView(radioButton)
            }

            layoutRadioGroup.invalidate()

            layoutRadioGroup.setOnCheckedChangeListener { rg, checkedId ->
                println(checkedId)
                if (checkedId != -1){
                    println(rg.checkedRadioButtonId)
                    viewModel.putFormDataValue(
                        when(rg.checkedRadioButtonId) {
                            0 -> "P01"
                            1 -> "P02"
                            2 -> "P03"
                            3 -> "P04"
                            4 -> "P05"
                            else -> ""
                        },
                        "1"
                    )
                }
            }

            next.setOnClickListener {
                goToSeventhPakar()
            }
            preview.setOnClickListener {
                requireActivity().onBackPressed()
            }
            desc.setOnClickListener {
                goToSixthPakar()
            }
        }
    }

    private fun setupData() {
        with(binding){
        }
    }

}