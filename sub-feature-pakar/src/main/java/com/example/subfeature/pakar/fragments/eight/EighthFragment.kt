package com.example.subfeature.pakar.fragments.eight

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.databinding.*
import com.example.subfeature.pakar.goToNinethPakar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EighthFragment : Fragment() {

    private lateinit var binding: FragmentEighthBinding

    private lateinit var layoutRadioGroup: RadioGroup

    private val viewModel: PakarEighthViewModel by sharedViewModel()

    private val data by lazy {
        listOf (
            Questions("C01", "Kesehatan"),
            Questions("C02", "Ekonomi"),
            Questions("C03", "Pendidikan"),
            Questions("C04", "Aparatur Sipil Negara"),
            Questions("C05", "Media")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEighthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                            0 -> "C01"
                            1 -> "C02"
                            2 -> "C03"
                            3 -> "C04"
                            4 -> "C05"
                            else -> ""
                        },
                        "1"
                    )
                }
            }

            next.setOnClickListener {
                goToNinethPakar()
            }
            preview.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}