package com.example.subfeature.hasilangket

import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceTypedOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.example.core_data.api.ApiEvent
import com.example.core_data.domain.LastResult
import com.example.core_data.domain.LastResults
import com.example.core_data.domain.isGuru
import com.example.core_resource.components.base.BaseFragment
import com.example.subfeature.hasilangket.databinding.FragmentHasilAngketBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HasilAngketFragment : BaseFragment<FragmentHasilAngketBinding>(FragmentHasilAngketBinding::inflate) {

    private val pakarViewModel: HasilAngketViewModel by sharedViewModel()

    override fun initView(){
        pakarViewModel.auth.observe(viewLifecycleOwner) { user ->
            user?.let {
                pakarViewModel.getLastResult(it.idSiswa.toInt(), if (it.isGuru) 1 else 0)
            }
        }
        pakarViewModel.lastResult.observe(viewLifecycleOwner) { lastResult ->
            when(lastResult) {
                is ApiEvent.OnProgress -> {

                }
                is ApiEvent.OnSuccess -> {
                    setupDataLastResult(lastResult.getData())
                }
                is ApiEvent.OnFailed -> {

                }
            }
        }
    }

    private fun setupDataLastResult(lastResults: LastResults?) {
        with(binding){
            rvLastResult.setup {
                withLayoutManager(LinearLayoutManager(context))
                withDataSource(dataSourceTypedOf(lastResults ?: emptyList()))
                withItem<LastResult, LastResultViewHolder>(R.layout.layout_item_last_result) {
                    onBind(::LastResultViewHolder) { _, lastResult ->
                        tvName.text = lastResult.nama
                        tvResult.text = lastResult.hasilAkhir
                    }
                }
            }
        }
    }

    override fun initListener() {
    }

}