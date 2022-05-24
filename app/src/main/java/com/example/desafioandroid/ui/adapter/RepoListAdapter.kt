package com.example.desafioandroid.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.desafioandroid.databinding.RepositoryItemBinding
import com.example.desafioandroid.model.Items


class RepoListAdapter : ListAdapter<Items, RepoListItemViewHolder>(RepoListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListItemViewHolder {
        val binding = RepositoryItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return RepoListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}