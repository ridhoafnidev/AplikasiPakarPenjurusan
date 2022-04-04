package com.example.subfeature.pakar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core_data.domain.ListOnBoardingData
import com.example.core_data.domain.OnBoardingData
import com.example.subfeature.pakar.databinding.LayoutItemBoardingBinding
import com.example.subfeature.pakar.viewholder.OnBoardingItemViewHolder

class OnBoardingItemAdapter(private val items: ListOnBoardingData) : RecyclerView.Adapter<OnBoardingItemViewHolder>() {

    //private lateinit var onItemClickCallback: OnItemClickCallback

//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingItemViewHolder {
        val view = LayoutItemBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OnBoardingItemViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: OnBoardingItemViewHolder, position: Int) {
        holder.bind(items[position])
        //holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(items[holder.adapterPosition]) }
    }

    override fun getItemCount() = items.size

//    interface OnItemClickCallback {
//        fun onItemClicked(data: OnBoardingData)
//    }

}