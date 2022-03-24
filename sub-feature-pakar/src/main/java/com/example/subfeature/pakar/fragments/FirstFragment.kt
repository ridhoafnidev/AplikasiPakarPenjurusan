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
import com.example.subfeature.pakar.viewholder.QuestionViewHolder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding

    private val pakarFirstViewModel: PakarFirstViewModel by sharedViewModel()

    private val dataFirst by lazy {
        listOf(
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
            Questions("N14", "Fakultas psikologi")
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
            //tvTitle.text = onBoardingItem.title
            rvFirst.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(dataFirst))
                withItem<Questions, QuestionViewHolder>(R.layout.layout_question){
                    onBind(::QuestionViewHolder){ index, data ->
                        titleCheckBox.text = data.question
                        titleCheckBox.setOnCheckedChangeListener { _, isChecked ->
                            pakarFirstViewModel.putFormDataValue(data.id, if(isChecked) "1" else "0")
                        }
                    }
                }
            }
        }
    }
}