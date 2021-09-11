package submission.andhiratobing.githubuser.view.fragments.home.detailuser

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.adapter.remote.paging.followingusers.FollowingAdapterPaging
import submission.andhiratobing.githubuser.adapter.remote.paging.followingusers.FollowingLoadStateAdapter
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.FragmentFollowingBinding
import submission.andhiratobing.githubuser.util.extension.hide
import submission.andhiratobing.githubuser.util.extension.show
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity
import submission.andhiratobing.githubuser.viewmodel.FollowingViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding
    private val followingViewModel: FollowingViewModel by viewModels()
    private lateinit var followingAdapter: FollowingAdapterPaging


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        setDataFollowing()
        initAdapter()
        onClickItem()
    }

    private fun setDataFollowing() {
        val username = arguments?.getString(DetailUserActivity.DATA_USER)
        if (username != null) followingViewModel.setFollowing(username)
    }

    private fun initObserver() {
        followingViewModel.getFollowing().observe(viewLifecycleOwner) {
            followingAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initAdapter() {
        binding.apply {
            followingAdapter = FollowingAdapterPaging()
            rvFollowing.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvFollowing.adapter = followingAdapter


            rvFollowing.adapter = followingAdapter.withLoadStateHeaderAndFooter(
                header = FollowingLoadStateAdapter { followingAdapter.retry() },
                footer = FollowingLoadStateAdapter { followingAdapter.retry() }
            )
            followingAdapter.addLoadStateListener { loadState ->
                rvFollowing.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                ivNotFound.isVisible = loadState.source.refresh is LoadState.Error
                tvMessage.isVisible = loadState.source.refresh is LoadState.Error

                //handling
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && followingAdapter.itemCount < 1
                ) {
                    rvFollowing.hide()
                    ivNotFound.show()
                    tvMessage.show()
                } else {
                    ivNotFound.hide()
                    tvMessage.hide()
                }

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    if (errorState.endOfPaginationReached) {
                        Snackbar.make(
                            binding.rvFollowing,
                            "${R.string.all_data_loaded}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.rvFollowing,
                            R.string.no_internet_connection,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun onClickItem() {
        followingAdapter.setOnItemClickCallBack(object :
            FollowingAdapterPaging.OnItemClickCallBack {
            override fun onItemClick(data: UserResponseItem) {
                val intent = Intent(requireContext(), DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.DATA_USER, data)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}