package submission.andhiratobing.githubuser.view.fragments.home.detailuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.adapter.remote.followingusers.FollowAdapter
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.databinding.FragmentFollowingBinding
import submission.andhiratobing.githubuser.util.network.NetworkState
import submission.andhiratobing.githubuser.viewmodel.FollowingViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel: FollowingViewModel by activityViewModels()
//    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var followingAdapter: FollowAdapter


    companion object {
        private const val ARG_USERNAME = "args_username"

        fun newInstance(username: String?): FollowingFragment {
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)

            return FollowingFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatusNetwork()
        initRecyclerView()
        initObserver()

        //get bundle from detail user fragment
        arguments?.getString(ARG_USERNAME).let { data ->
            Log.d("getUsername", "$data")
            CoroutineScope(Dispatchers.Main).launch {
                data?.let { followingViewModel.getFollowing(it) }
            }
        }


    }

    private fun initObserver(){
        followingViewModel.setFollowing().observe(viewLifecycleOwner) { following ->
            followingAdapter.setList(following as ArrayList<FollowingResponse>)
            Log.d("Data", "${followingAdapter.itemCount}")
        }
    }

    private fun initStatusNetwork(){
        followingViewModel.setNetworkState().observe(viewLifecycleOwner,{ network ->
            binding.apply {
                progressBar.visibility = if (network == NetworkState.LOADING) View.VISIBLE else View.GONE
                tvMessage.visibility = if (network == NetworkState.FAILED) View.VISIBLE else View.GONE
            }
        })
    }


    private fun initRecyclerView() {
        binding.apply {
            followingAdapter = FollowAdapter()
            rvFollowing.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvFollowing.setHasFixedSize(true)
            rvFollowing.adapter = followingAdapter

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}