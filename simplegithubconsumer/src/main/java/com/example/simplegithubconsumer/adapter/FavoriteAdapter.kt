package com.example.simplegithubconsumer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.simplegithubconsumer.data.FavoriteUser
import com.example.simplegithubconsumer.databinding.ItemFavoriteUserBinding
import com.example.simplegithubconsumer.util.image.LoadImage.loadImage
import com.example.simplegithubconsumer.util.number.NumberFormat.asFormattedDecimals


class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.FavoriteViewHolder>(FAVORITE_COMPARATOR) {

    inner class FavoriteViewHolder(private val binding: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FavoriteUser) {
            with(binding) {
                tvName.text = data.name
                tvUsername.text = data.username
                tvFollowing.text = data.following.asFormattedDecimals()
                tvFollowers.text = data.followers.asFormattedDecimals()
                tvRepository.text = data.repository.asFormattedDecimals()
                ivAvatar.loadImage(data.avatar)
                if (data.name.isNullOrEmpty()) binding.tvName.isVisible = false
                else tvName.isVisible = true

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteViewHolder(
            ItemFavoriteUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val FAVORITE_COMPARATOR = object : DiffUtil.ItemCallback<FavoriteUser>() {
            override fun areItemsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser,
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser,
            ): Boolean = oldItem == newItem
        }
    }

}