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
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.adapter.followersusers.FollowersAdapter
import submission.andhiratobing.githubuser.data.remote.responses.followers.FollowersResponse
import submission.andhiratobing.githubuser.databinding.FragmentFollowersBinding
import submission.andhiratobing.githubuser.viewmodel.FollowersViewModel

@AndroidEntryPoint
class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private val followersViewModel: FollowersViewModel by activityViewModels()
    private lateinit var followersAdapter: FollowersAdapter


    companion object {
        private const val ARG_USERNAME = "args_username"

        fun newInstance(username: String?): FollowersFragment {
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)

            return FollowersFragment().apply {
                arguments = bundle
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        arguments?.getString(ARG_USERNAME).let { data ->
            Log.d("getUsername", "$data")
            CoroutineScope(Dispatchers.Main).launch {
                data?.let { followersViewModel.getFolloers(it) }
            }
        }

        followersViewModel.setFollowers().observe(viewLifecycleOwner) { following ->
            followersAdapter.setList(following as ArrayList<FollowersResponse>)
            Log.d("Data", "${followersAdapter.itemCount}")

        }
    }



    private fun initRecyclerView() {
        binding.apply {
            followersAdapter = FollowersAdapter()
            rvFollowers.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvFollowers.setHasFixedSize(true)
            rvFollowers.adapter = followersAdapter

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}