package submission.andhiratobing.githubuser.adapter.remote.paging.reposusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.databinding.ItemReposBinding
import submission.andhiratobing.githubuser.util.color.ColorLanguage
import submission.andhiratobing.githubuser.util.extension.NumberFormat.asFormattedDecimals

class RepoSAdapterPaging :
    PagingDataAdapter<ReposResponse, RepoSAdapterPaging.ReposViewHolder>(
        REPOS_COMPARATOR
    ) {

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

    inner class ReposViewHolder(private val binding: ItemReposBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reposResponse: ReposResponse) {
            binding.apply {
                val color = ColorLanguage
                color.setColorLangauge(ivColorLanguage, reposResponse.language)
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
}

