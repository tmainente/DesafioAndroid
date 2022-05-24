 package com.example.desafioandroid.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.desafioandroid.model.Items

 class RepoListDiffCallback(

) : DiffUtil.ItemCallback<Items>() {

    override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
        return oldItem == newItem
    }
}