package com.example.subfeature.pakar.fragments.nine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.core_data.domain.Answer
import com.example.core_data.domain.Questions
import com.example.core_data.domain.isGuru
import com.example.subfeature.pakar.databinding.*
import com.example.subfeature.pakar.fragments.eight.PakarEighthViewModel
import com.example.subfeature.pakar.fragments.five.PakarFifthViewModel
import com.example.subfeature.pakar.fragments.four.PakarFourthViewModel
import com.example.subfeature.pakar.fragments.one.PakarFirstViewModel
import com.example.subfeature.pakar.fragments.seven.PakarSeventhViewModel
import com.example.subfeature.pakar.fragments.three.PakarThirdViewModel
import com.example.subfeature.pakar.fragments.two.PakarSecondViewModel
import com.example.subfeature.pakar.goToResultPakar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class NinethFragment : Fragment() {

    private lateinit var binding: FragmentNinethBinding

    private lateinit var layoutRadioGroup: RadioGroup

    private val firstPakarViewModel: PakarFirstViewModel by sharedViewModel()
    private val secondPakarViewModel: PakarSecondViewModel by sharedViewModel()
    private val thirdPakarViewModel: PakarThirdViewModel by sharedViewModel()
    private val fourthPakarViewModel: PakarFourthViewModel by sharedViewModel()
    private val fifthPakarViewModel: PakarFifthViewModel by sharedViewModel()
    private val seventhPakarViewModel: PakarSeventhViewModel by sharedViewModel()
    private val eighthPakarViewModel: PakarEighthViewModel by sharedViewModel()
    private val ninethPakarViewModel: PakarNinethViewModel by sharedViewModel()

    private val data by lazy {
        listOf (
            Questions("D01", "Matematika dan Ilmu Alam"), Questions("D02", "Peminatan Ilmu-ilmu Sosial")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNinethBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInput()
        setupObserver()
    }

    private fun setupObserver() {
        ninethPakarViewModel.isDataSaved.observe(viewLifecycleOwner){
            if (it){
                goToResultPakar()
            }
        }
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
                    ninethPakarViewModel.putFormDataValue(
                        when(rg.checkedRadioButtonId) {
                            0 -> "D01"
                            1 -> "D02"
                            else -> ""
                        },
                        "1"
                    )
                }
            }

            ninethPakarViewModel.auth.observe(viewLifecycleOwner) { user ->
                user?.let {
                   ninethPakarViewModel.siswaId = it.idUser
                }
            }
            next.setOnClickListener {
                val data = arrayListOf<Answer>()
                data.addAll(firstPakarViewModel.getFinalData())
                data.addAll(secondPakarViewModel.getFinalData())
                data.addAll(thirdPakarViewModel.getFinalData())
                data.addAll(fourthPakarViewModel.getFinalData())
                data.addAll(fifthPakarViewModel.getFinalData())
                data.addAll(seventhPakarViewModel.getFinalData())
                data.addAll(eighthPakarViewModel.getFinalData())
                data.addAll(ninethPakarViewModel.getFinalData())

                ninethPakarViewModel.saveQuestionAnswer(ninethPakarViewModel.siswaId, data)
            }
        }
    }

}