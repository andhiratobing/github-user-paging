package submission.andhiratobing.githubuser.view.fragments.search

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.data.remote.adapter.SearchAdapter
import submission.andhiratobing.githubuser.data.remote.adapter.SearchLoadStateAdapter
import submission.andhiratobing.githubuser.databinding.FragmentSearchBinding
import submission.andhiratobing.githubuser.viewmodel.SearchViewModel

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var searchAdapter = SearchAdapter()
    private val searchViewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initAdapter()
        initProcess()
        initSearch()
    }

    private fun initProcess() {
        searchViewModel.search.observe(viewLifecycleOwner, {
            searchAdapter.submitData(lifecycle, it)
        })
    }

    private fun initAdapter() {
        binding.apply {
            searchAdapter = SearchAdapter()
            rvUser.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvUser.setHasFixedSize(true)

            rvUser.adapter = searchAdapter.withLoadStateHeaderAndFooter(
                header = SearchLoadStateAdapter { searchAdapter.retry() },
                footer = SearchLoadStateAdapter { searchAdapter.retry() }
            )
            searchAdapter.addLoadStateListener { loadState ->

                progressBar.isVisible = loadState.source.refresh is LoadState.Loading

                rvUser.isVisible = loadState.source.refresh is LoadState.NotLoading

                //error handling no result searching
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && searchAdapter.itemCount < 1
                ) {
                    rvUser.isVisible = false
                    tvSearchNoResult.isVisible = true
                } else {
                    rvUser.isVisible = true
                    tvSearchNoResult.isVisible = false
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
                        AlertDialog.Builder(requireActivity())
                            .setTitle(R.string.error)
                            .setMessage(R.string.no_internet_connection)
                            .setNegativeButton(R.string.cancel) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .setPositiveButton(R.string.retry) { _, _ ->
                                searchAdapter.retry()
                            }.show()
                    }
                }
            }
        }
    }

    private fun initSearch() {
        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    binding.rvUser.scrollToPosition(0)
                    searchViewModel.searchUser(it)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}