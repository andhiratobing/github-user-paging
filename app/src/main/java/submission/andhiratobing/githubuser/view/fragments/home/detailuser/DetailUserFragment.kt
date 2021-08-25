package submission.andhiratobing.githubuser.view.fragments.home.detailuser

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
import submission.andhiratobing.githubuser.databinding.FragmentDetailUserBinding
import submission.andhiratobing.githubuser.util.extension.NumberFormat.asFormattedDecimals
import submission.andhiratobing.githubuser.viewmodel.DetailUserViewModel
import submission.andhiratobing.githubuser.viewmodel.FavoriteViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailUserFragmentArgs>()
    private val detailUserViewModel: DetailUserViewModel by viewModels()
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var sectionPageAdapter: SectionPageAdapter

    companion object {
        const val DATA_USER = "data_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFollow.setOnClickListener {
            Snackbar.make(it, getString(R.string.follow_message), Snackbar.LENGTH_LONG).show()
        }

        getDataFromParcelable()
        initDetailUserViewModel()
        initTabLayout()

    }

    private fun initDetailUserViewModel() {
        val args = args.user
        val username = args.username
        detailUserViewModel.getDetailUsers(username)

        detailUserViewModel.setDetailUsers().observe(viewLifecycleOwner, { data ->

            when {
                data.name === null -> {
                    binding.tvName.isVisible = false
                }
                else -> {
                    binding.tvName.isVisible = true
                }
            }
            when {
                data?.bio === null -> {
                    binding.tvBio.isVisible = false
                }
                else -> {
                    binding.tvBio.isVisible = true
                }
            }
            when {
                data.company === null -> {
                    binding.tvCompany.isVisible = false
                    binding.ivCompany.isVisible = false
                }
                else -> {
                    binding.tvCompany.isVisible = true
                    binding.ivCompany.isVisible = true
                }
            }

            when {
                data.location === null -> {
                    binding.tvLocation.isVisible = false
                    binding.ivLocation.isVisible = false
                }
                else -> {
                    binding.tvLocation.isVisible = true
                    binding.ivLocation.isVisible = true
                }
            }


            binding.apply {
                tvName.text = data.name
                tvCompany.text = data.company.toString()
                tvBio.text = data.bio
                tvLocation.text = data.location
                tvFollowing.text = data.following.asFormattedDecimals()
                tvFollowers.text = data.followers.asFormattedDecimals()
                tvRepository.text = data.repository.asFormattedDecimals()
            }


            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val count: Int = favoriteViewModel.getCountFavoriteUsers(data.id)
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
                    favoriteViewModel.addFavoriteUser(
                        data.id,
                        data.username,
                        data.name,
                        data.avatar,
                        data.company,
                        data.bio,
                        data.location,
                        data.following,
                        data.followers,
                        data.repository)
                    Snackbar.make(it,
                        "Successfully added ${data.username} to favorites",
                        Snackbar.LENGTH_SHORT).show()
                } else {
                    favoriteViewModel.deleteFavorite(data.id)
                }
                Snackbar.make(it,
                    "Successfully removed ${data.username} from favorites",
                    Snackbar.LENGTH_SHORT).show()
            }
            binding.toggleFav.isChecked = isChecked
        })

    }


    private fun getDataFromParcelable() {
        //retrive data using navigation args
        val arguments = args.user
        binding.apply {
            //Set toolbar with value
            toolbarDetailUser.title = arguments.username
            (activity as AppCompatActivity).setSupportActionBar(toolbarDetailUser)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

            tvUsername.text = arguments.username
            Glide.with(this@DetailUserFragment).load(arguments.avatar)
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

    private fun clickShare() {
        val arguments = args.user
        val type = "text/plain"
        ShareCompat.IntentBuilder(requireActivity())
            .setType(type)
            .setChooserTitle("Share")
            .setText(
                resources.getString(
                    R.string.share_detail_user,
                    "${arguments.username}\n" +
                            "image: ${arguments.avatar}"
                )
            )
            .startChooser()
    }


    private fun initTabLayout() {

        val bundle = Bundle()
        bundle.putString(DATA_USER, args.user.username)

        binding.apply {
            sectionPageAdapter = SectionPageAdapter(this@DetailUserFragment, bundle)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail_user, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
            R.id.itemShare -> {
                clickShare()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}




