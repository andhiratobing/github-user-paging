package submission.andhiratobing.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.data.model.UserEntity
import submission.andhiratobing.githubuser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.HomeViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private var listDataUser = ArrayList<UserEntity>()


    fun setListDataUser(listData: ArrayList<UserEntity>) {
        listDataUser.clear()
        listDataUser.addAll(listData)
        notifyDataSetChanged()
    }


    inner class HomeViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserEntity) {
            with(binding) {
                tvName.text = data.name
                tvCompany.text = data.company
                tvFollowing.text = data.following.toString()
                tvFollowers.text = data.follower.toString()
                tvRepository.text = data.repository.toString()

                val resourcesImage = itemView.resources.getIdentifier(
                    data.avatar,
                    "drawable",
                    itemView.context.packageName
                )
                Glide.with(itemView.context)
                    .load(resourcesImage)
                    .placeholder(submission.andhiratobing.githubuser.R.drawable.placeholder_image)
                    .into(ivAvatar)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listDataUser[position])

        holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClick(listDataUser[holder.bindingAdapterPosition])
        }
    }

    override fun getItemCount(): Int = listDataUser.size


    //Click callback item
    interface OnItemClickCallBack {
        fun onItemClick(data: UserEntity)
    }

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }
}

