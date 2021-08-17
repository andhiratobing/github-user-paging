package submission.andhiratobing.githubuser.view.activities.detailuser

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.model.UserEntity
import submission.andhiratobing.githubuser.databinding.ActivityDetailUserBinding
import submission.andhiratobing.githubuser.viewmodel.DetailUserViewModel

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val detailUserViewModel: DetailUserViewModel by viewModels()

    companion object {
        const val DATA_USER = "data_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        checkFavorite()
    }

    private fun checkFavorite() {
        val data: UserEntity = intent.getParcelableExtra(DATA_USER)!!
        var isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = detailUserViewModel.getCountFavorite(data.username)
            withContext(Dispatchers.Main) {
                if (count != null) {
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
                detailUserViewModel.addFavorite(
                    data.username,
                    data.name,
                    data.avatar,
                    data.company,
                    data.location,
                    data.repository,
                    data.follower,
                    data.following
                )
                Snackbar.make(
                    it,
                    "Successfully added ${data.name} to favorites",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                detailUserViewModel.deleteFavorite(data.username)
                Snackbar.make(
                    it,
                    "Successfully removed ${data.name} from favorites",
                    Snackbar.LENGTH_LONG
                ).show()
            }
            binding.toggleFav.isChecked = isChecked
        }
    }


    private fun getData() {
        val data: UserEntity = intent.getParcelableExtra(DATA_USER)!!
        binding.apply {
            //Set toolbar with value
            toolbarDetailUser.title = data.username
            setSupportActionBar(toolbarDetailUser)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            tvName.text = data.name
            tvUsername.text = data.username
            tvCompany.text = data.company
            tvLocation.text = data.location
            tvFollowing.text = data.following.toString()
            tvFollowers.text = data.follower.toString()
            tvRepository.text = data.repository.toString()

            val resourcesImage = resources.getIdentifier(
                data.avatar,
                "drawable", packageName
            )
            Glide.with(this@DetailUserActivity).load(resourcesImage)
                .placeholder(R.drawable.placeholder_image)
                .into(ivAvatar)
        }
    }

    private fun clickShare() {
        val data: UserEntity = intent.getParcelableExtra(DATA_USER)!!
        val type = "text/plain"
        ShareCompat.IntentBuilder(this)
            .setType(type)
            .setChooserTitle("Share")
            .setText(
                resources.getString(
                    R.string.share_detail_user,
                    "${data.name}\n" +
                            "Username:${data.username}\n" +
                            "Company: ${data.company}"
                )
            )
            .startChooser()
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