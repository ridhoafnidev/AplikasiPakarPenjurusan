package com.example.subfeature.pakar.fragments.one

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.databinding.FragmentFirstBinding
import com.example.subfeature.pakar.goToSecondPakar
import com.example.subfeature.pakar.goToThirdPakar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private val pakarFirstViewModel: PakarFirstViewModel by sharedViewModel()

    private lateinit var layoutRadioGroup: RadioGroup

    private val dataFirst by lazy {
        listOf(
            Questions("J01", "IPA"),
            Questions("J02", "IPS"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupData()
    }

    private fun setupData() {
        with(binding){

            layoutRadioGroup = radioGroup

            dataFirst.forEachIndexed { indexOption, option ->
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
                    pakarFirstViewModel.putFormDataValue(
                        if (rg.checkedRadioButtonId == 0) "J01" else "J02", "1")
                }
            }

            btnNext.setOnClickListener {
                pakarFirstViewModel.getFinalData().forEach {
                    if (it.answer == "J01") goToSecondPakar()
                    else goToThirdPakar()
                }
            }

        }
    }
}