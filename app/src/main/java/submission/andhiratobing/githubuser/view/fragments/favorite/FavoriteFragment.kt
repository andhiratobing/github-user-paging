package submission.andhiratobing.githubuser.view.fragments.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import submission.andhiratobing.githubuser.R
import submission.andhiratobing.githubuser.adapter.local.FavoriteAdapter
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.databinding.FragmentFavoriteBinding
import submission.andhiratobing.githubuser.util.extension.hide
import submission.andhiratobing.githubuser.util.extension.number.NumberFormat.asFormattedDecimals
import submission.andhiratobing.githubuser.util.extension.show
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity
import submission.andhiratobing.githubuser.viewmodel.FavoriteViewModel

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding as FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.swipeRefreshLayout.setOnRefreshListener {
            countFavoriteUser()
            favoriteViewModel.getAllFavoriteUser().observe(viewLifecycleOwner, {
                favoriteAdapter.submitList(it)
                binding.swipeRefreshLayout.isRefreshing = false
            })
        }

        initRecyclerView()
        initViewModelFavorite()
        countFavoriteUser()
    }

    private fun countFavoriteUser() {
        CoroutineScope(Dispatchers.Main).launch {
            val count = favoriteViewModel.getCountUsers()
            if (count > 0) {
                "(${count.asFormattedDecimals()} ${resources.getString(R.string.users)})".also {
                    binding.tvCountFavorite.text = it
                }
                binding.apply {
                    tvCountFavorite.show()
                    ivFavoriteEmpty.hide()
                    tvEmptyFavorite.hide()
                }
            } else {
                binding.apply {
                    tvCountFavorite.hide()
                    ivFavoriteEmpty.show()
                    tvEmptyFavorite.show()
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            favoriteAdapter = FavoriteAdapter()
            rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
            rvFavorite.adapter = favoriteAdapter

            //Click item
            favoriteAdapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallBack {

                override fun onItemClick(data: FavoriteEntity) {
                    val user = UserResponseItem(data.username, data.id, data.avatar)
                    val intent = Intent(requireActivity(), DetailUserActivity::class.java)
                    intent.putExtra(DetailUserActivity.DATA_USER, user)
                    startActivity(intent)
                }

            })
        }
    }

    private fun initViewModelFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                favoriteViewModel.getAllFavoriteUser().observe(viewLifecycleOwner, {
                    if (it != null) {
                        favoriteAdapter.submitList(it as ArrayList<FavoriteEntity>)
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}