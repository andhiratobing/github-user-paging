package submission.andhiratobing.githubuser.data.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.databinding.ItemUserBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private var listDataUser = ArrayList<FavoriteEntity>()

    interface OnItemClickCallBack {
        fun onItemClick(data: FavoriteEntity)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setListDataUser(listData: List<FavoriteEntity>) {
        listDataUser = listData as ArrayList<FavoriteEntity>
    }

    inner class FavoriteViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavoriteEntity) {
            with(binding) {
                tvUsername.text = data.username

                Glide.with(itemView.context)
                    .load(data.avatar)
                    .placeholder(submission.andhiratobing.githubuser.R.drawable.placeholder_image)
                    .into(ivAvatar)
            }


            binding.root.setOnClickListener {
                onItemClickCallBack.onItemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val list = listDataUser[position]
        holder.bind(list)

    }

    override fun getItemCount(): Int = listDataUser.size
}