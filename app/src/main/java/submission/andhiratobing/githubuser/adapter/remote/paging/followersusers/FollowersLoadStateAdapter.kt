package submission.andhiratobing.githubuser.adapter.remote.paging.followersusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.databinding.LayoutLoadStateBinding

class FollowersLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<FollowersLoadStateAdapter.FollowersLoadStateViewHolder>() {

    inner class FollowersLoadStateViewHolder(
        private val binding: LayoutLoadStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.tvLoadStateRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBarLoadSatate.isVisible = loadState is LoadState.Loading
                tvLoadStateRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FollowersLoadStateViewHolder {
        val binding =
            LayoutLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowersLoadStateViewHolder(binding, retry)
    }

    override fun onBindViewHolder(holder: FollowersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}

