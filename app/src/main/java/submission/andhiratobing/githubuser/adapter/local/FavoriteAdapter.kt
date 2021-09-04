package submission.andhiratobing.githubuser.adapter.local

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.databinding.ItemFavoriteUserBinding
import submission.andhiratobing.githubuser.util.extension.image.LoadImage.loadImage
import submission.andhiratobing.githubuser.util.extension.number.NumberFormat.asFormattedDecimals

class FavoriteAdapter :
    ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(FAVORITE_COMPARATOR) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    interface OnItemClickCallBack {
        fun onItemClick(data: FavoriteEntity)
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FavoriteEntity) {
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

            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val itemPosition = getItem(bindingAdapterPosition)
                    if (itemPosition != null) {
                        onItemClickCallBack.onItemClick(data)
                    }
                }
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
        private val FAVORITE_COMPARATOR = object : DiffUtil.ItemCallback<FavoriteEntity>() {
            override fun areItemsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity,
            ): Boolean = oldItem.id == newItem.id


            override fun areContentsTheSame(
                oldItem: FavoriteEntity,
                newItem: FavoriteEntity,
            ): Boolean = oldItem == newItem
        }
    }

}