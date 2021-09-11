package submission.andhiratobing.githubuser.view.activities

import android.content.Intent
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
import submission.andhiratobing.githubuser.util.Constants.Companion.TYPE_SHARE
import submission.andhiratobing.githubuser.util.extension.hide
import submission.andhiratobing.githubuser.util.extension.number.NumberFormat.asFormattedDecimals
import submission.andhiratobing.githubuser.util.extension.show
import submission.andhiratobing.githubuser.util.state.ResourceState
import submission.andhiratobing.githubuser.viewmodel.DetailUserViewModel
import submission.andhiratobing.githubuser.viewmodel.FavoriteViewModel
import submission.andhiratobing.githubuser.widget.FavoriteWidget

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var sectionPageAdapter: SectionPageAdapter

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
                    tvName.hide()
                } else {
                    tvName.text = it.name
                    tvName.show()
                }
                if (it.company.isNullOrEmpty()) {
                    tvCompany.hide()
                    ivCompany.hide()
                } else {
                    tvCompany.text = it.company
                    tvCompany.show()
                    ivCompany.show()
                }
                if (it.bio.isNullOrEmpty()) {
                    tvBio.hide()
                } else {
                    tvBio.text = it.bio
                    tvBio.show()
                }
                if (it.location.isNullOrEmpty()) {
                    ivLocation.hide()
                    tvLocation.hide()
                } else {
                    tvLocation.text = it.location
                    ivLocation.show()
                    tvLocation.show()
                }

                tvFollowing.text = it.following.asFormattedDecimals()
                tvFollowers.text = it.followers.asFormattedDecimals()
                tvRepository.text = it.repository.asFormattedDecimals()
            }

            addFavoriteUser(it)
            sendDataFavoriteToWidgetUpdate()
        })
    }

    private fun initStatusNetwork() {
        detailUserViewModel.setNetworkState().observe(this, { status ->
            binding.apply {
                progressBar.visibility = if (status == ResourceState.LOADING) View.VISIBLE else View.GONE
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
        val type = TYPE_SHARE
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
                    0 -> {
                        tab.text = resources.getString(R.string.followers)
                        tab.setIcon(R.drawable.ic_followers)
                    }
                    1 -> {
                        tab.text = resources.getString(R.string.following)
                        tab.setIcon(R.drawable.ic_following)
                    }
                    2 -> {
                        tab.text = resources.getString(R.string.repository)
                        tab.setIcon(R.drawable.ic_repository)
                    }
                }
            }.attach()
        }
    }

    private fun sendDataFavoriteToWidgetUpdate() {
        sendBroadcast(Intent(this, FavoriteWidget::class.java).apply {
            action = FavoriteWidget.EXTRA_FAVORITE_WIDGET_UPDATE
        })
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

    companion object {
        const val DATA_USER = "data_user"
    }

}




