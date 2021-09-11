package submission.andhiratobing.githubuser.adapter.remote.paging.reposusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.databinding.ItemReposBinding
import submission.andhiratobing.githubuser.util.extension.color.ColorLanguage
import submission.andhiratobing.githubuser.util.extension.number.NumberFormat.asFormattedDecimals

class ReposAdapterPaging :
    PagingDataAdapter<ReposResponse, ReposAdapterPaging.ReposViewHolder>(
        REPOS_COMPARATOR
    ) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack

    //Click callback item
    interface OnItemClickCallBack {
        fun onItemClick(reposResponse: ReposResponse)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    inner class ReposViewHolder(private val binding: ItemReposBinding) :
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

        fun bind(reposResponse: ReposResponse) {
            binding.apply {
                val color = ColorLanguage
                color.setColorLanguage(ivColorLanguage, reposResponse.language)
                tvNameRepos.text = reposResponse.name
                tvDescription.text = reposResponse.description
                tvLanguage.text = reposResponse.language
                tvCountStar.text = reposResponse.starCount.asFormattedDecimals()
                tvCountFork.text = reposResponse.forksCount.asFormattedDecimals()
                tvCountIssue.text = reposResponse.issuesCount.asFormattedDecimals()

                if (reposResponse.description.isNullOrEmpty()) {
                    tvDescription.visibility = View.GONE
                } else {
                    tvDescription.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposViewHolder =
        ReposViewHolder(
            ItemReposBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val REPOS_COMPARATOR = object : DiffUtil.ItemCallback<ReposResponse>() {
            override fun areItemsTheSame(
                oldItem: ReposResponse,
                newItem: ReposResponse,
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ReposResponse,
                newItem: ReposResponse,
            ): Boolean = oldItem == newItem
        }
    }

}

