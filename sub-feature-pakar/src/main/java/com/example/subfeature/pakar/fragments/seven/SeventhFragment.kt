package com.example.subfeature.pakar.fragments.seven

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.databinding.*
import com.example.subfeature.pakar.goToEighthPakar
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SeventhFragment : Fragment() {

    private lateinit var binding: FragmentSeventhBinding

    private lateinit var layoutRadioGroup: RadioGroup

    private val viewModel: PakarSeventhViewModel by sharedViewModel()

    private val data by lazy {
        listOf (
            Questions("N01", "Fakultas ekonomi dan bisnis"),
            Questions("N02", "Fakultas farmasi"),
            Questions("N03", "Fakultas hukum"),
            Questions("N04", "Fakultas ilmu keperawatan"),
            Questions("N05", "Fakultas ilmu komputer"),
            Questions("N06", "Fakultas kedokteran"),
            Questions("N07", "Fakultas kedokteran gigi"),
            Questions("N08", "Fakultas kesehatan masyarakat"),
            Questions("N09", "Fakultas teknik"),
            Questions("N10", "Fakultas ilmu pengetahuan budaya"),
            Questions("N11", "Fakultas matematika dan ilmu pengetahuan alam"),
            Questions("N12", "Fakultas sosial dan politik"),
            Questions("N13", "Fakultas pendidikan fokasi"),
            Questions("N14", "Fakultas psikologi"),
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSeventhBinding.inflate(layoutInflater)
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
                            0 -> "N01"
                            1 -> "N02"
                            2 -> "N03"
                            3 -> "N04"
                            4 -> "N05"
                            5 -> "N06"
                            6 -> "N07"
                            7 -> "N08"
                            8 -> "N09"
                            9 -> "N10"
                            10 -> "N11"
                            11 -> "N12"
                            12 -> "N13"
                            13 -> "N14"
                            else -> ""
                        },
                        "1"
                    )
                }
            }

            next.setOnClickListener {
                goToEighthPakar()
            }
            preview.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}