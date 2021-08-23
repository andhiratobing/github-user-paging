//package submission.andhiratobing.githubuser.data.local.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import submission.andhiratobing.githubuser.data.local.entities.UserFavoriteItem
//import submission.andhiratobing.githubuser.databinding.ItemUserBinding
//
//class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
//
//    private lateinit var onItemClickCallBack: OnItemClickCallBack
//    private var listDataUser = ArrayList<UserFavoriteItem>()
//
//    interface OnItemClickCallBack {
//        fun onItemClick(data: UserFavoriteItem)
//    }
//
//    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
//        this.onItemClickCallBack = onItemClickCallBack
//    }
//
//    fun setListDataUser(listData: ArrayList<UserFavoriteItem>) {
//        listDataUser.clear()
//        listDataUser.addAll(listData)
//        notifyDataSetChanged()
//    }
//
//    inner class FavoriteViewHolder(private val binding: ItemUserBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        fun bind(data: UserFavoriteItem) {
//            with(binding) {
//                tvName.text = data.name
//                tvUsername.text = data.username
//                tvFollowing.text = data.following.toString()
//                tvFollowers.text = data.follower.toString()
//                tvRepository.text = data.repository.toString()
//
//                val resourcesImage = itemView.resources.getIdentifier(
//                    data.avatar,
//                    "drawable",
//                    itemView.context.packageName
//                )
//                Glide.with(itemView.context)
//                    .load(resourcesImage)
//                    .placeholder(submission.andhiratobing.githubuser.R.drawable.placeholder_image)
//                    .into(ivAvatar)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
//        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return FavoriteViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
//        val list = listDataUser[position]
//        holder.bind(list)
//
//        holder.itemView.setOnClickListener {
//            onItemClickCallBack.onItemClick(listDataUser[holder.bindingAdapterPosition])
//        }
//    }
//
//    override fun getItemCount(): Int = listDataUser.size
//}