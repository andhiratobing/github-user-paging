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
import submission.andhiratobing.githubuser.data.remote.adapter.followingusers.FollowingAdapter
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.databinding.FragmentFollowingBinding
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity.Companion.DATA_USER
import submission.andhiratobing.githubuser.viewmodel.FollowingViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FollowingFragment : Fragment() {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private val followingViewModel: FollowingViewModel by activityViewModels()
    private lateinit var followingAdapter: FollowingAdapter


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
        initViewModel()
    }

    private fun initViewModel() {
        //get bundle from detail user fragment
        val username = arguments?.getString(DATA_USER).toString()
        CoroutineScope(Dispatchers.Main).launch {
            followingViewModel.getFollowing(username)
        }

    }

    private fun initRecyclerView() {
        binding.apply {
            rvFollowing.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvFollowing.setHasFixedSize(true)
            followingAdapter = FollowingAdapter()
            rvFollowing.adapter = followingAdapter

            followingViewModel.setFollowing().observe(viewLifecycleOwner, {
                if (it != null) {
                    followingAdapter.submitList(it as ArrayList<FollowingResponse>)
                    Log.d("Data", "${followingAdapter.itemCount}")
                }
            })
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}