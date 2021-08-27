package submission.andhiratobing.githubuser.data.remote.adapter.reposusers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.databinding.ItemReposBinding

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ReposViewHolder>() {

    var list: ArrayList<ReposResponse> = ArrayList()

    fun setList(list: List<ReposResponse>){
        this.list = list as ArrayList<ReposResponse>
        notifyDataSetChanged()
    }

    inner class ReposViewHolder(private val binding: ItemReposBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(reposResponse: ReposResponse) {
            binding.apply {
                tvNameRepos.text = reposResponse.name
                tvDescription.text = reposResponse.description
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
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}