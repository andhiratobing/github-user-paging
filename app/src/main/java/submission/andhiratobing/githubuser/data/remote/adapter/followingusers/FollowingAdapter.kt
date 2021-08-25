package submission.andhiratobing.githubuser.data.remote.adapter.followingusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.databinding.ItemUserBinding

class FollowingAdapter :
    ListAdapter<FollowingResponse, FollowingAdapter.FollowingViewHolder>(FOLLOWING_COMPARATOR) {

    companion object {
        private val FOLLOWING_COMPARATOR = object : DiffUtil.ItemCallback<FollowingResponse>() {
            override fun areItemsTheSame(
                oldItem: FollowingResponse,
                newItem: FollowingResponse,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: FollowingResponse,
                newItem: FollowingResponse,
            ): Boolean = oldItem == newItem
        }
    }


    inner class FollowingViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: FollowingResponse){
                with(binding){
                    tvUsername.text = data.username

                    Glide.with(itemView.context)
                        .load(data.avatar)
                        .placeholder(R.drawable.placeholder_image)
                        .into(ivAvatar)
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingViewHolder =
        FollowingViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))


    override fun onBindViewHolder(holder: FollowingViewHolder, position: Int) =
        holder.bind(getItem(position))
}

