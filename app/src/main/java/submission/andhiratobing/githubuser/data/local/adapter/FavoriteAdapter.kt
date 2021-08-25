package submission.andhiratobing.githubuser.data.local.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.data.local.entities.UserEntity
import submission.andhiratobing.githubuser.databinding.ItemUserBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private var listDataUser = ArrayList<UserEntity>()

    interface OnItemClickCallBack {
        fun onItemClick(data: UserEntity)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }

    fun setListDataUser(listData: List<UserEntity>) {
        listDataUser.clear()
        listDataUser = listData as ArrayList<UserEntity>
        notifyDataSetChanged()
    }

    inner class FavoriteViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: UserEntity) {
            with(binding) {
                tvUsername.text = data.username

                Glide.with(itemView.context)
                    .load(data.avatar)
                    .placeholder(submission.andhiratobing.githubuser.R.drawable.placeholder_image)
                    .into(ivAvatar)
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

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClick(listDataUser[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listDataUser.size
}