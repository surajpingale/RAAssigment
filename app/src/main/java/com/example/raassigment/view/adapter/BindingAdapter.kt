package com.example.raassigment.view.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.raassigment.R
import com.example.raassigment.model.dataclasses.Facilities
import com.example.raassigment.model.dataclasses.Option

//@BindingAdapter(value = ["setOptions", "listener"])
//fun RecyclerView.setOptions(
//    optionList: List<Option>,
//    onItemClick: FacilityOptionsAdapter.OnItemClick
//) {
//    if (optionList != null) {
//        val optionsAdapter = FacilityOptionsAdapter(onItemClick)
//        setHasFixedSize(true)
//        adapter = optionsAdapter
//        optionsAdapter.updateOptionsList(optionList)
//    }
//}

private var optionsAdapterList: ArrayList<FacilityOptionsAdapter> = ArrayList()

@BindingAdapter(value = ["setOptions", "index", "listener"])
fun RecyclerView.setOptions(
    facilities: Facilities,
    index: Int,
    onItemClick: FacilityOptionsAdapter.OnItemClick
) {

    if (facilities != null) {
        val optionsList = facilities.facilities[index].options
        val optionsAdapter = FacilityOptionsAdapter(optionsList,onItemClick, facilities, index)
        setHasFixedSize(true)
        optionsAdapterList.add(optionsAdapter)
        adapter = optionsAdapter
//        optionsAdapter.updateOptionsList(optionsList)
        onItemClick.getAdapterInstance(optionsAdapter, optionsList)
    }
}

fun updateOptionsList(optionsList: List<Option>, index: Int) {
    optionsAdapterList[index].updateOptionsList(optionsList)
}


@BindingAdapter(value = ["imageUrl"])
fun loadImage(
    view: ImageView, icon: String
) {
    if (icon != null) {
        // Property type icons
        when (icon) {
            "apartment" -> {
                Glide.with(view.context)
                    .load(R.drawable.apartment)
                    .into(view)
            }
            "condo" -> {
                Glide.with(view.context)
                    .load(R.drawable.condo)
                    .into(view)
            }
            "boat" -> {
                Glide.with(view.context)
                    .load(R.drawable.boat)
                    .into(view)
            }
            "land" -> {
                Glide.with(view.context)
                    .load(R.drawable.land)
                    .into(view)
            }

            // Number of rooms icons
            "rooms" -> {
                Glide.with(view.context)
                    .load(R.drawable.rooms)
                    .into(view)
            }
            "no-room" -> {
                Glide.with(view.context)
                    .load(R.drawable.no_room)
                    .into(view)
            }

            // Other facilities icons
            "swimming" -> {
                Glide.with(view.context)
                    .load(R.drawable.swimming)
                    .into(view)
            }
            "garden" -> {
                Glide.with(view.context)
                    .load(R.drawable.garden)
                    .into(view)
            }
            "garage" -> {
                Glide.with(view.context)
                    .load(R.drawable.garage)
                    .into(view)
            }
        }
    }
}

