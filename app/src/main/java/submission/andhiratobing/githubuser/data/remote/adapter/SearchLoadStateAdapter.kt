package submission.andhiratobing.githubuser.data.remote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.databinding.LayoutLoadStateBinding

class SearchLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<SearchLoadStateAdapter.UserLoadStateViewHolder>() {

    inner class UserLoadStateViewHolder(
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
                tvLoadStateError.isVisible = loadState !is LoadState.Loading

                if (loadState is LoadState.Error) {
                    tvLoadStateError.text = loadState.error.localizedMessage

                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UserLoadStateViewHolder {
        val binding =
            LayoutLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserLoadStateViewHolder(binding, retry)
    }


    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}

