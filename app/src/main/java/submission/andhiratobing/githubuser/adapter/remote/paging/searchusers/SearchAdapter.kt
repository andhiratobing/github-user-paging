package submission.andhiratobing.githubuser.adapter.remote.paging.searchusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.ItemUserBinding
import submission.andhiratobing.githubuser.util.extension.image.LoadImage.loadImage

class SearchAdapter :
    PagingDataAdapter<UserResponseItem, SearchAdapter.SearchViewHolder>(SEARCH_USER_COMPARATOR) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    //Click callback item
    interface OnItemClickCallBack {
        fun onItemClick(data: UserResponseItem)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class SearchViewHolder(private val binding: ItemUserBinding) :
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

        fun bind(userItem: UserResponseItem) {
            binding.apply {
                tvUsername.text = userItem.username
                ivAvatar.loadImage(userItem.avatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val SEARCH_USER_COMPARATOR = object : DiffUtil.ItemCallback<UserResponseItem>() {
            override fun areItemsTheSame(
                oldItem: UserResponseItem,
                newItem: UserResponseItem
            ): Boolean = (oldItem.id == newItem.id)

            override fun areContentsTheSame(
                oldItem: UserResponseItem,
                newItem: UserResponseItem
            ): Boolean = oldItem == newItem

        }
    }

}