package submission.andhiratobing.githubuser.adapter.remote.paging.followingusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.ItemUserBinding

class FollowingAdapterPaging :
    PagingDataAdapter<UserResponseItem, FollowingAdapterPaging.FollowingViewHolder>(
        FOLLOWING_COMPARATOR) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    //click callback item
    interface OnItemClickCallBack {
        fun onItemClick(data: UserResponseItem)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    companion object {
        private val FOLLOWING_COMPARATOR = object : DiffUtil.ItemCallback<UserResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserResponseItem,
                newItem: UserResponseItem,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: UserResponseItem,
                newItem: UserResponseItem,
            ): Boolean = oldItem == newItem
        }
    }


    inner class FollowingViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                    val itemPosition = getItem(bindingAdapterPosition)
                    if (itemPosition != null) {
                        onItemClickCallBack.onItemClick(itemPosition)
                    }
                }
            }
        }

        fun bind(data: UserResponseItem) {
            binding.apply {
                tvUsername.text = data.username

                Glide.with(itemView.context)
                    .load(data.avatar)
                    .placeholder(R.drawable.placeholder_image)
                    .into(ivAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder =
        FollowingViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

