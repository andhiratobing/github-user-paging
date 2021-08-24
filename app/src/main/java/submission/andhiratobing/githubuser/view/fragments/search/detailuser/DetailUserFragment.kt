package submission.andhiratobing.githubuser.view.fragments.search.detailuser

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.databinding.FragmentDetailUserBinding
import submission.andhiratobing.githubuser.viewmodel.DetailUserViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class DetailUserFragment : Fragment() {

    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<DetailUserFragmentArgs>()
    private val detailUserViewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromParcelable()

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
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = true
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
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
                resources.getString(R.string.share_detail_user,
                    "${arguments.username}\n" +
                            "image: ${arguments.avatar}"
                )
            )
            .startChooser()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail_user, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressedDispatcher.hasEnabledCallbacks()
                requireActivity().onBackPressedDispatcher.onBackPressed()
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

//    override fun onDestroyView() {
//        super.onDestroyView()
//        val isNavigated = false
//        if (!isNavigated)
//            requireActivity().onBackPressedDispatcher.addCallback(this){
//                val navController = findNavController()
//                if (navController.currentBackStackEntry?.destination.id != null){
//                    navController.popBackStack(),tr)
//                }
//            }
//    }

}


//    private fun checkFavorite() {
//        val data: UserFavorite? = intent.getParcelableExtra(DATA_USER)
//        var isChecked = false
//        CoroutineScope(Dispatchers.IO).launch {
//            val count = data?.username?.let { detailUserViewModel.getCountFavorite(it) }
//            withContext(Dispatchers.Main) {
//                if (count != null) {
//                    if (count > 0) {
//                        binding.toggleFav.isChecked = true
//                        isChecked = true
//                    } else {
//                        binding.toggleFav.isChecked = false
//                        isChecked = false
//                    }
//                }
//            }
//        }
//
//        binding.toggleFav.setOnClickListener {
//            isChecked = !isChecked
//            if (isChecked) {
//                data?.username?.let { dataUser ->
//                    detailUserViewModel.addFavorite(
//                        dataUser,
//                        data.name,
//                        data.avatar,
//                        data.company,
//                        data.location,
//                        data.repository,
//                        data.follower,
//                        data.following
//                    )
//                }
//                Snackbar.make(
//                    it,
//                    "Successfully added ${data?.name} to favorites",
//                    Snackbar.LENGTH_LONG
//                ).show()
//            } else {
//                data?.let { dataUser -> detailUserViewModel.deleteFavorite(dataUser.username) }
//                Snackbar.make(
//                    it,
//                    "Successfully removed ${data?.name} from favorites",
//                    Snackbar.LENGTH_LONG
//                ).show()
//            }
//            binding.toggleFav.isChecked = isChecked
//        }
//    }


