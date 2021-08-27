package submission.andhiratobing.githubuser.view.fragments.home.detailuser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.remote.adapter.followingusers.FollowAdapter
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.databinding.FragmentFollowingBinding
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
        initLoading(true)
        followingViewModel.setFollowing().observe(viewLifecycleOwner) { following ->
            followingAdapter.setList(following as ArrayList<FollowingResponse>)
            Log.d("Data", "${followingAdapter.itemCount}")
            initLoading(false)
        }
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

    private fun initLoading(state: Boolean){
        binding.progressBar.isVisible = when(state){
            true -> true
            false -> false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}