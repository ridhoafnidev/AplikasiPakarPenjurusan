package com.example.subfeature.pakar.fragments.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.core_navigation.ModuleNavigator
import com.example.subfeature.pakar.databinding.*
import com.example.subfeature.pakar.fragments.nine.PakarNinethViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResultFragment : Fragment(), ModuleNavigator {

    private lateinit var binding: FragmentResultBinding

    private val viewModel: PakarNinethViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupInput()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.result.observe(viewLifecycleOwner) {
            binding.tvResult.text = it
        }
    }

    private fun setupInput() {
        with(binding){
            back.setOnClickListener {
                navigateToHomeActivity(finishCurrent = true)
            }
        }
    }

}