package com.example.subfeature.pakar.fragments.six

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.example.core_data.domain.Questions
import com.example.subfeature.pakar.R
import com.example.subfeature.pakar.databinding.*
import com.example.subfeature.pakar.viewholder.DescViewHolder
import com.example.subfeature.pakar.viewholder.QuestionViewHolder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SixthFragment : Fragment() {

    private lateinit var binding: FragmentSixthBinding

    private val data by lazy {
        listOf (
            Questions("Kesehatan", "(Dokter, Perawat, Apoteker, Ahli Gizi, Bidang Ahli lab, Klinik dan Medis, Radiografer, Penyuluh Kesehatan, Ahli Epidemiologi, dll)"),
            Questions("Ekonomi", "(Analisi Kredit, Konsultan Ekonomi, Petugas Kredit, Pengusaha Pangan, Petugas Bank, Analisis Kuantitatif, dll)"),
            Questions("Pendidikan","(Guru, Dosen, Kepala Sekolah, Penulis Buku, Pustakawan, Daycare Staf, Pelatih Olahraga)"),
            Questions("Aparatur Sipil Negara", "(TNI, Polisi, Jaksa, Hukum, dll)"),
            Questions("Media", "(Wartawan, Jurnalis, Reporter, dll)")
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSixthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInput()
    }

    private fun setupInput() {
        with(binding){
            rvSixth.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(data))
                withItem<Questions, DescViewHolder>(R.layout.layout_item_desc){
                    onBind(::DescViewHolder){ index, data ->
                        title.text = data.id
                        desc.text = data.question
                    }
                }
            }

            preview.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }
    }

}