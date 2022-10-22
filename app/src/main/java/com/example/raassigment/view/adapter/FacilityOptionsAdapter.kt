package com.example.raassigment.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.raassigment.R
import com.example.raassigment.databinding.ItemFacilityOptionBinding
import com.example.raassigment.model.dataclasses.Facilities
import com.example.raassigment.model.dataclasses.Option
import com.example.raassigment.utils.Constants
import com.example.raassigment.utils.OptionsDiffUtil

class FacilityOptionsAdapter(
    private var optionsList: List<Option>,
    private val onItemClick: OnItemClick,
    private val facilities: Facilities,
    private val index: Int
) :
    RecyclerView.Adapter<FacilityOptionsAdapter.OptionsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemFacilityOptionBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_facility_option,
            parent,
            false
        )
        return OptionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionsViewHolder, position: Int) {
        val option = optionsList[position]



        holder.binding.apply {
            options = option
            itemClickListener = onItemClick
            executePendingBindings()
            holder.setData(option)

        }

        holder.itemView.setOnClickListener {
            val facilityId = facilities.facilities[index].facilityId
            onItemClick.itemClicked(optionsList, option, index, facilityId)
        }
    }


    override fun getItemCount(): Int {
        return optionsList.size
    }

    fun updateOptionsList(newList: List<Option>) {
        val diffUtil = OptionsDiffUtil(optionsList, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        optionsList = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class OptionsViewHolder(itemView: ItemFacilityOptionBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemFacilityOptionBinding

        init {
            binding = itemView
        }

        fun selectedBg() {
            itemView.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.bg_item_selected
            )
        }

        fun defaultBg() {
            itemView.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.bg_item_not_selected
            )
        }

        fun setData(option: Option) {

            if (option.isSelected == Constants.SELECTED) {
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.bg_item_selected
                )
            } else if (option.isSelected == Constants.UNSELECTED) {
                itemView.background = ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.bg_item_not_selected
                )
            } else {
                itemView.background = null
            }
        }
    }


    interface OnItemClick {
        fun itemClicked(
            optionsList: List<Option>,
            option: Option,
            adapterPosition: Int,
            facilityId: String
        )

        fun getAdapterInstance(optionsAdapter: FacilityOptionsAdapter, optionsList: List<Option>)

    }

}