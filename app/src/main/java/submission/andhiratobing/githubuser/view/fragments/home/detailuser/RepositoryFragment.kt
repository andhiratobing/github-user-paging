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
import submission.andhiratobing.githubuser.adapter.remote.paging.reposusers.ReposAdapterPaging
import submission.andhiratobing.githubuser.adapter.remote.paging.reposusers.ReposLoadStateAdapter
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.databinding.FragmentRepositoryBinding
import submission.andhiratobing.githubuser.util.extension.hide
import submission.andhiratobing.githubuser.util.extension.show
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity
import submission.andhiratobing.githubuser.view.activities.ReposWebActivity
import submission.andhiratobing.githubuser.view.activities.ReposWebActivity.Companion.HTML_URL
import submission.andhiratobing.githubuser.viewmodel.RepositoryViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RepositoryFragment : Fragment() {

    private var _binding: FragmentRepositoryBinding? = null
    private val binding get() = _binding as FragmentRepositoryBinding
    private val repositoryViewModel: RepositoryViewModel by viewModels()
    private lateinit var reposAdapter: ReposAdapterPaging

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRepositoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObserver()
        initAdapter()
        setDataRepos()
        reposToWebView()
    }

    private fun setDataRepos() {
        val username = arguments?.getString(DetailUserActivity.DATA_USER)
        if (username != null) repositoryViewModel.setRepository(username)
    }

    private fun reposToWebView() {
        reposAdapter.setOnItemClickCallBack(object : ReposAdapterPaging.OnItemClickCallBack {
            override fun onItemClick(reposResponse: ReposResponse) {
                val intent = Intent(requireActivity(), ReposWebActivity::class.java)
                intent.putExtra(HTML_URL, reposResponse)
                requireActivity().startActivity(intent)
            }
        })
    }

    private fun initObserver() {
        repositoryViewModel.getRepository().observe(viewLifecycleOwner) {
            reposAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    private fun initAdapter() {
        binding.apply {
            reposAdapter = ReposAdapterPaging()
            rvRepos.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rvRepos.adapter = reposAdapter


            rvRepos.adapter = reposAdapter.withLoadStateHeaderAndFooter(
                header = ReposLoadStateAdapter { reposAdapter.retry() },
                footer = ReposLoadStateAdapter { reposAdapter.retry() }
            )
            reposAdapter.addLoadStateListener { loadState ->
                rvRepos.isVisible = loadState.source.refresh is LoadState.NotLoading
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                ivNotFound.isVisible = loadState.source.refresh is LoadState.Error
                tvMessage.isVisible = loadState.source.refresh is LoadState.Error

                //handling
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached && reposAdapter.itemCount < 1
                ) {
                    rvRepos.hide()
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
                            binding.rvRepos,
                            "${R.string.all_data_loaded}",
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.rvRepos,
                            R.string.no_internet_connection,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}