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
import submission.andhiratobing.githubuser.adapter.remote.paging.followersusers.FollowersAdapterPaging
import submission.andhiratobing.githubuser.adapter.remote.paging.followersusers.FollowersLoadStateAdapter
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.FragmentFollowersBinding
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity.Companion.DATA_USER
import submission.andhiratobing.githubuser.viewmodel.FollowersViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding as FragmentFollowersBinding
    private val followersViewModel: FollowersViewModel by viewModels()
    private lateinit var followersAdapter: FollowersAdapterPaging

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initAdapter()
        setDataFollowers()
        onClickItem()
    }

    private fun setDataFollowers() {
        val username = arguments?.getString(DATA_USER)
        if (username != null) followersViewModel.setFollowers(username)
    }

    private fun initObserver() {
        followersViewModel.getFollowers().observe(viewLifecycleOwner) {
            followersAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initAdapter() {
        binding.apply {
            followersAdapter = FollowersAdapterPaging()
            rvFollowers.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = followersAdapter


            rvFollowers.adapter = followersAdapter.withLoadStateHeaderAndFooter(
                header = FollowersLoadStateAdapter { followersAdapter.retry() },
                footer = FollowersLoadStateAdapter { followersAdapter.retry() }
            )
            followersAdapter.addLoadStateListener { loadState ->
                rvFollowers.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                ivNotFound.isVisible = loadState.source.refresh is LoadState.Error
                tvMessage.isVisible = loadState.source.refresh is LoadState.Error

                //handling
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && followersAdapter.itemCount < 1) {
                    rvFollowers.isVisible = false
                    ivNotFound.isVisible = true
                    tvMessage.isVisible = true
                }else {
                    ivNotFound.isVisible = false
                    tvMessage.isVisible = false
                }

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    if (errorState.endOfPaginationReached) {
                        Snackbar.make(binding.rvFollowers, "${R.string.all_data_loaded}", Snackbar.LENGTH_LONG).show()
                    } else {
                        Snackbar.make(binding.rvFollowers, R.string.no_internet_connection, Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun onClickItem(){
        followersAdapter.setOnItemClickCallBack(object : FollowersAdapterPaging.OnItemClickCallBack{
            override fun onItemClick(data: UserResponseItem) {
                val intent = Intent(requireContext(), DetailUserActivity::class.java)
                intent.putExtra(DATA_USER, data)
                startActivity(intent)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
