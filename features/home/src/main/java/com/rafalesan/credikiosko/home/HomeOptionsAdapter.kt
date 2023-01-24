package com.rafalesan.credikiosko.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rafalesan.credikiosko.home.databinding.HolderHomeOptionItemBinding

class HomeOptionsAdapter(private val onOptionSelected: (HomeOption) -> Unit) : ListAdapter<HomeOption, HomeOptionsAdapter.HomeOptionViewHolder>(
    HomeOptionDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeOptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderHomeOptionItemBinding.inflate(inflater, parent, false)
        return HomeOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeOptionViewHolder, position: Int) {
        val homeOption = getItem(position)
        holder.bind(homeOption, onOptionSelected)
    }

    class HomeOptionViewHolder(private val binding: HolderHomeOptionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(homeOption: HomeOption, onOptionSelected: (HomeOption) -> Unit) {
            binding.homeOption = homeOption
            binding.cardView.setOnClickListener {
                onOptionSelected.invoke(homeOption)
            }
        }

    }

    object HomeOptionDiffCallback : DiffUtil.ItemCallback<HomeOption>() {

        override fun areItemsTheSame(oldItem: HomeOption, newItem: HomeOption): Boolean {
            return oldItem.optionNameResId == newItem.optionNameResId
        }

        override fun areContentsTheSame(oldItem: HomeOption, newItem: HomeOption): Boolean {
            return oldItem.optionNameResId == newItem.optionNameResId &&
                   oldItem.optionIconResId == newItem.optionIconResId &&
                   oldItem.actionIdRes == newItem.actionIdRes
        }

    }

}
