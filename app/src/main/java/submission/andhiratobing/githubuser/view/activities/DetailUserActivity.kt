package submission.andhiratobing.githubuser.view.activities

import android.os.Bundle
import android.view.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.adapter.SectionPageAdapter
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.ActivityDetailUserBinding
import submission.andhiratobing.githubuser.util.extension.NumberFormat.asFormattedDecimals
import submission.andhiratobing.githubuser.util.state.ResourceState
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

        initStatusNetwork()
        initObserver()
        getDataFromParcelable()
        getDetailUser()
        initTabLayout()
    }

    private fun initObserver() {
        detailUserViewModel.setDetailUsers().observe(this, {
            binding.apply {
                if (it.name.isNullOrEmpty()) {
                    tvName.visibility = View.GONE
                } else {
                    tvName.text = it.name
                    tvName.visibility = View.VISIBLE
                }
                if (it.company.isNullOrEmpty()) {
                    tvCompany.visibility = View.GONE
                    ivCompany.visibility = View.GONE
                } else {
                    tvCompany.text = it.company
                    tvCompany.visibility = View.VISIBLE
                    ivCompany.visibility = View.VISIBLE
                }
                if (it.bio.isNullOrEmpty()) {
                    tvBio.visibility = View.GONE
                } else {
                    tvBio.text = it.bio
                    tvBio.visibility = View.VISIBLE
                }
                if (it.location.isNullOrEmpty()) {
                    ivLocation.visibility = View.GONE
                    tvLocation.visibility = View.GONE
                } else {
                    tvLocation.text = it.location
                    ivLocation.visibility = View.VISIBLE
                    tvLocation.visibility = View.VISIBLE
                }

                tvFollowing.text = it.following.asFormattedDecimals()
                tvFollowers.text = it.followers.asFormattedDecimals()
                tvRepository.text = it.repository.asFormattedDecimals()
            }

            addFavoriteUser(it)
        })
    }

    private fun initStatusNetwork() {
        detailUserViewModel.setNetworkState().observe(this, { status ->
            binding.apply {
                progressBar.visibility =
                    if (status == ResourceState.LOADING) View.VISIBLE else View.GONE
            }
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
                    "${dataFromDetailUserr.username} ${resources.getString(R.string.success_add_favorite)}",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                favoriteViewModel.deleteFavorite(dataFromDetailUserr.id)
                Snackbar.make(
                    it,
                    "${dataFromDetailUserr.username} ${resources.getString(R.string.remove_favorite)}",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
        binding.toggleFav.isChecked = isChecked
    }


    private fun getDataFromParcelable() {
        val data: UserResponseItem? = intent.getParcelableExtra(DATA_USER)
        if (data != null) {
            binding.apply {
                //Set toolbar with value
                toolbarDetailUser.title = data.username
                setSupportActionBar(toolbarDetailUser)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)

                tvUsername.text = data.username
                Glide.with(this@DetailUserActivity).load(data.avatar)
                    .placeholder(R.drawable.placeholder_image)
                    .into(ivAvatar)
            }
        }
    }

    private fun clickShare() {
        val data: UserResponseItem? = intent.getParcelableExtra(DATA_USER)
        val type = "text/plain"
        ShareCompat.IntentBuilder(this)
            .setType(type)
            .setChooserTitle(getString(R.string.share))
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
        val bundle = Bundle()
        bundle.putString(DATA_USER, data?.username)
        binding.apply {
            sectionPageAdapter = SectionPageAdapter(supportFragmentManager, lifecycle, bundle)
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




