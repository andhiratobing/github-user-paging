package submission.andhiratobing.githubuser.view.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.remote.adapter.SectionPageAdapter
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.remote.responses.searchusers.UserResponseItem
import submission.andhiratobing.githubuser.databinding.ActivityDetailUserBinding
import submission.andhiratobing.githubuser.util.extension.NumberFormat.asFormattedDecimals
import submission.andhiratobing.githubuser.viewmodel.DetailUserViewModel
import submission.andhiratobing.githubuser.viewmodel.FavoriteViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var sectionPageAdapter: SectionPageAdapter

    companion object {
        const val DATA_USER = "data_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFollow.setOnClickListener {
            Snackbar.make(it, getString(R.string.follow_message), Snackbar.LENGTH_LONG).show()
        }

        initObserver()
        getDataFromParcelable()
        getDetailUser()
        initTabLayout()

    }

    private fun initObserver(){
        detailUserViewModel.setDetailUsers().observe(this, { dataObserveDetailUser ->
//            if (dataObserveDetailUser == null) {
//                binding.tvName.visibility = View.GONE
//                binding.tvBio.visibility = View.GONE
//                binding.tvCompany.visibility = View.GONE
//                binding.ivCompany.visibility = View.GONE
//                binding.tvLocation.visibility = View.GONE
//                binding.ivLocation.visibility = View.GONE
//            } else {
            binding.apply {
                tvName.text = dataObserveDetailUser.name
                tvCompany.text = dataObserveDetailUser.company
                tvBio.text = dataObserveDetailUser.bio
                tvLocation.text = dataObserveDetailUser.location
                tvFollowing.text = dataObserveDetailUser.following.asFormattedDecimals()
                tvFollowers.text = dataObserveDetailUser.followers.asFormattedDecimals()
                tvRepository.text = dataObserveDetailUser.repository.asFormattedDecimals()
            }
            addFavoriteUser(dataObserveDetailUser)
        })
    }



    private fun getDetailUser() {
        val dataFromUserResponse: UserResponseItem? = intent.getParcelableExtra(DATA_USER)
        val username = dataFromUserResponse?.username
        if (username != null) {
            CoroutineScope(Dispatchers.Main).launch {
                detailUserViewModel.getDetailUsers(username)
            }
        }
    }

    private fun addFavoriteUser(dataFromDetailUserr: DetailUserResponse) {
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                val count: Int = favoriteViewModel.getCountFavoriteUsers(dataFromDetailUserr.id)
                withContext(Dispatchers.Main) {
                    if (count > 0) {
                        binding.toggleFav.isChecked = true
                        isChecked = true
                    } else {
                        binding.toggleFav.isChecked = false
                        isChecked = false
                    }
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            isChecked = !isChecked
            if (isChecked) {
                favoriteViewModel.addFavoriteUser(dataFromDetailUserr)
                Snackbar.make(
                    it,
                    "Successfully added ${dataFromDetailUserr.username} to favorites",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                favoriteViewModel.deleteFavorite(dataFromDetailUserr.id)
            }
            Snackbar.make(
                it,
                "Successfully removed ${dataFromDetailUserr.username} from favorites",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        binding.toggleFav.isChecked = isChecked
    }


    private fun getDataFromParcelable() {
        val data: UserResponseItem? = intent.getParcelableExtra(DATA_USER)

        if (data != null){
            binding.progressBar.isVisible = true
            binding.apply {
                //Set toolbar with value
                toolbarDetailUser.title = data.username
                setSupportActionBar(toolbarDetailUser)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                tvUsername.text = data.username
                Glide.with(this@DetailUserActivity).load(data.avatar)
                    .placeholder(R.drawable.placeholder_image)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            progressBar.isVisible = true
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean,
                        ): Boolean {
                            tvUsername.isVisible = true
                            progressBar.isVisible = false
                            return false
                        }
                    })
                    .into(ivAvatar)
            }
        }
    }

    private fun clickShare() {
        val data: UserResponseItem? = intent.getParcelableExtra(DATA_USER)
        val type = "text/plain"
        ShareCompat.IntentBuilder(this)
            .setType(type)
            .setChooserTitle("Share")
            .setText(
                resources.getString(
                    R.string.share_detail_user,
                    "${data?.username}\n" +
                            "image: ${data?.avatar}"
                )
            )
            .startChooser()
    }


    private fun initTabLayout() {
        val data: UserResponseItem? = intent.getParcelableExtra(DATA_USER)
        binding.apply {
            sectionPageAdapter = SectionPageAdapter(supportFragmentManager, lifecycle)
            sectionPageAdapter.username = data?.username
            viewPager.adapter = sectionPageAdapter



            //set tablayout mediator
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.setIcon(R.drawable.ic_following)
                    1 -> tab.setIcon(R.drawable.ic_followers)
                    2 -> tab.setIcon(R.drawable.ic_repository)
                }
            }.attach()

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.itemShare -> {
                clickShare()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}




