package com.example.raassigment.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.raassigment.model.dataclasses.Option

class OptionsDiffUtil(
    private val oldList: List<Option>,
    private val newList: List<Option>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].isSelected != newList[newItemPosition].isSelected -> {
                false
            }

            else -> true
        }
    }
}