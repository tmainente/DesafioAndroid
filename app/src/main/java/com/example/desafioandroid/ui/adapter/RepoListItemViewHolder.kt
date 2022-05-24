 package com.example.desafioandroid.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.desafioandroid.R
import com.example.desafioandroid.databinding.RepositoryItemBinding
import com.example.desafioandroid.model.Items

import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class RepoListItemViewHolder(
    val binding: RepositoryItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(repo: Items) {
        repo.name?.let {
            binding.nameRepo.text = it
        }

        repo.description?.let {
            binding.descriptionRepo.text = it
        }
        repo.forks?.let {
            binding.qtFork.text = it.toString()
        }
        repo.stargazersCount?.let {
            binding.qtStarred.text = it.toString()
        }

        repo.owner?.login?.let{
            binding.nameProfile.text = it
        }

        repo.owner?.avatarUrl?.let {

            Picasso.get()
                .load(it)
                .error(R.drawable.ic_round_account_circle)
                .into(binding.imgProfile)
        }
    }
}