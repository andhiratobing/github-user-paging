package submission.andhiratobing.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import submission.andhiratobing.githubuser.data.model.UserEntity
import submission.andhiratobing.githubuser.databinding.ItemUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.HomeViewHolder>(), Filterable {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private var listDataUser: ArrayList<UserEntity> = ArrayList()
    private var listDataUserFilter: ArrayList<UserEntity> = ArrayList()

    fun setListDataUser(listData: ArrayList<UserEntity>) {
        this.listDataUser.clear()
        this.listDataUser = this.listDataUserFilter
        this.listDataUser.addAll(listData)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(char: CharSequence?): FilterResults {
                val charString = char?.toString() ?: ""
                listDataUser = if (charString.isEmpty()) listDataUserFilter else {
                    val filteredList = ArrayList<UserEntity>()
                    listDataUserFilter.filter {
                        (it.name.lowercase().contains(charString.lowercase()))
                                || (it.username.lowercase().contains(charString.lowercase()))
                                || (it.following.toString().lowercase()
                            .contains(charString.lowercase()))
                                || (it.follower.toString().lowercase()
                            .contains(charString.lowercase()))
                                || (it.repository.toString().lowercase()
                            .contains(charString.lowercase()))
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = listDataUser }
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                listDataUser = if (p1?.values == null)
                    ArrayList()
                else
                    p1.values as ArrayList<UserEntity>
                notifyDataSetChanged()
            }

        }
    }


    inner class HomeViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: UserEntity) {
            with(binding) {
                tvName.text = data.name
                tvUsername.text = data.username
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
        val list = listDataUser[position]
        holder.bind(list)

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

