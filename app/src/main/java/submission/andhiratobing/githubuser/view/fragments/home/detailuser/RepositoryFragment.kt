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
import submission.andhiratobing.githubuser.adapter.remote.reposusers.ReposAdapter
import submission.andhiratobing.githubuser.databinding.FragmentRepositoryBinding
import submission.andhiratobing.githubuser.util.network.NetworkState
import submission.andhiratobing.githubuser.viewmodel.RepositoryViewModel

@AndroidEntryPoint
class RepositoryFragment : Fragment() {


    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding!!
    private val repositoryViewModel: RepositoryViewModel by activityViewModels()
    private lateinit var reposAdapter: ReposAdapter

    companion object {
        private const val ARG_USERNAME = "args_username"

        fun newInstance(username: String?): RepositoryFragment {
            val bundle = Bundle()
            bundle.putString(ARG_USERNAME, username)

            return RepositoryFragment().apply {
                arguments = bundle
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get bundle from detail user fragment
        arguments?.getString(ARG_USERNAME).let { data ->
            Log.d("getUsername", "$data")
            CoroutineScope(Dispatchers.Main).launch {
                data?.let { repositoryViewModel.getRepository(it) }
            }
        }

        initStatusNetwork()
        initRecyclerView()
        initObserver()

    }

    private fun initObserver(){
        repositoryViewModel.setRepository().observe(viewLifecycleOwner) { repos ->
            reposAdapter.setList(repos)
            Log.d("Data", "${reposAdapter.itemCount}")
        }
    }

    private fun initStatusNetwork(){
        repositoryViewModel.setNetworkState().observe(viewLifecycleOwner,{ network ->
            binding.apply {
                progressBar.visibility = if (network == NetworkState.LOADING) View.VISIBLE else View.GONE
                tvMessage.visibility = if (network == NetworkState.FAILED) View.VISIBLE else View.GONE
            }
        })
    }


    private fun initRecyclerView() {
        binding.apply {
            reposAdapter = ReposAdapter()
            rvRepos.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvRepos.setHasFixedSize(true)
            rvRepos.adapter = reposAdapter

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}