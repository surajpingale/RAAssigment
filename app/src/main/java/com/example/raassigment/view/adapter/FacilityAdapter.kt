package com.example.raassigment.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.raassigment.R
import com.example.raassigment.databinding.ItemFacilityBinding
import com.example.raassigment.model.dataclasses.Facilities
import com.example.raassigment.model.dataclasses.Facility
import com.example.raassigment.model.dataclasses.Option

class FacilityAdapter(
    private var facilitiesFromFrag: Facilities,
    private val onItemClicked: FacilityOptionsAdapter.OnItemClick
) :
    RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val facilityBinding: ItemFacilityBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_facility,
            parent,
            false
        )

        return FacilityViewHolder(facilityBinding)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        val facility = facilitiesFromFrag.facilities[position]


        holder.binding.apply {
            facility.index = holder.adapterPosition
            this.facility = facility
            this.facilities = facilitiesFromFrag
            this.onItemClick = onItemClicked
            rvFacilityOptions.setRecycledViewPool(viewPool)
            executePendingBindings()
        }

    }

    override fun getItemCount(): Int {
        return facilitiesFromFrag.facilities.size
    }


    inner class FacilityViewHolder(itemView: ItemFacilityBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        var binding: ItemFacilityBinding

        init {
            binding = itemView
        }
    }

}