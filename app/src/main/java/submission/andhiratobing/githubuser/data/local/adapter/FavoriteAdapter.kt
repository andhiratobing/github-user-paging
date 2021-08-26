package submission.andhiratobing.githubuser.data.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.databinding.ItemFavoriteUserBinding
import submission.andhiratobing.githubuser.util.extension.NumberFormat.asFormattedDecimals

class FavoriteAdapter :
    ListAdapter<FavoriteEntity, FavoriteAdapter.FavoriteViewHolder>(FAVORITE_COMPARATOR) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }


    interface OnItemClickCallBack {
        fun onItemClick(data: FavoriteEntity)
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


    inner class FavoriteViewHolder(private val binding: ItemFavoriteUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: FavoriteEntity) {
            with(binding) {
                tvName.text = data.name
                tvUsername.text = data.username
                tvFollowing.text = data.following.asFormattedDecimals()
                tvFollowers.text = data.followers.asFormattedDecimals()
                tvRepository.text = data.repository.asFormattedDecimals()

                Glide.with(itemView.context)
                    .load(data.avatar)
                    .placeholder(R.drawable.placeholder_image)
                    .into(ivAvatar)

                binding.tvName.isVisible = data.name.isNotBlank()

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

}