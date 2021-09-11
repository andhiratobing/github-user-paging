package submission.andhiratobing.githubuser.view.fragments.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import submission.andhiratobing.githubuser.adapter.remote.paging.searchusers.SearchAdapter
import submission.andhiratobing.githubuser.adapter.remote.paging.searchusers.SearchLoadStateAdapter
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.FragmentHomeBinding
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity
import submission.andhiratobing.githubuser.viewmodel.SearchViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private var searchAdapter = SearchAdapter()
    private val searchViewModel: SearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initProcess()
        initSearch()

        //send data to detail user
        onClickListener()

        binding.swipeRefreshLayout.setOnRefreshListener {
            searchViewModel.search.observe(viewLifecycleOwner, {
                searchAdapter.submitData(lifecycle, it)
                searchAdapter.retry()
                binding.swipeRefreshLayout.isRefreshing = false
            })
        }
    }

    private fun initProcess() {
        searchViewModel.search.observe(viewLifecycleOwner, {
            searchAdapter.submitData(lifecycle, it)
            Log.d("data", "$it")
        })
    }

    private fun initAdapter() {
        binding.apply {
            searchAdapter = SearchAdapter()
            rvUser.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvUser.setHasFixedSize(true)


            rvUser.adapter = searchAdapter.withLoadStateFooter(
                footer = SearchLoadStateAdapter { searchAdapter.retry() }
            )
            searchAdapter.addLoadStateListener { loadState ->

                rvUser.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading

                //handling searching
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && searchAdapter.itemCount <= 0
                ) {
                    rvUser.isVisible = false
                    tvSearchNoResult.isVisible = true
                    tvTitlePersons.isVisible = false
                } else if (searchAdapter.itemCount <= 0) {
                    rvUser.isVisible = false
                    tvSearchNoResult.isVisible = false
                    tvTitlePersons.isVisible = false
                } else {
                    rvUser.isVisible = true
                    tvSearchNoResult.isVisible = false
                    tvTitlePersons.isVisible = true
                }

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    if (errorState.endOfPaginationReached) {
                        Snackbar.make(
                            binding.rvUser,
                            "${R.string.all_data_loaded}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.rvUser,
                            R.string.no_internet_connection,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun initSearch() {
        binding.searchUser.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let { query ->
                    binding.rvUser.scrollToPosition(0)
                    searchViewModel.getSearchUser(query)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun onClickListener() {
        searchAdapter.setOnItemClickCallBack(object : SearchAdapter.OnItemClickCallBack {
            override fun onItemClick(data: UserResponseItem) {
                val intent = Intent(requireActivity(), DetailUserActivity::class.java)
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