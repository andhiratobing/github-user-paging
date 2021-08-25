package submission.andhiratobing.githubuser.view.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import submission.andhiratobing.githubuser.data.local.adapter.FavoriteAdapter
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.data.remote.responses.searchusers.UserResponseItem
import submission.andhiratobing.githubuser.databinding.FragmentFavoriteBinding
import submission.andhiratobing.githubuser.viewmodel.FavoriteViewModel

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
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

        initRecyclerView()
        initViewModelFavorite()
        countFavoriteUser()
    }


    private fun countFavoriteUser() {
        CoroutineScope(Dispatchers.Main).launch {
            val count = favoriteViewModel.getCountUsers()
            if (count > 0) {
                "($count users)".also { binding.tvCountFavorite.text = it }
                binding.tvCountFavorite.visibility = View.VISIBLE
            } else {
                binding.tvCountFavorite.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            favoriteAdapter = FavoriteAdapter()
            rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
            rvFavorite.adapter = favoriteAdapter
            rvFavorite.setHasFixedSize(true)
            //Click item
            favoriteAdapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallBack {

                override fun onItemClick(data: FavoriteEntity) {
                    val user = UserResponseItem( data.username,data.id, data.avatar)
                    val action = FavoriteFragmentDirections.actionNavFavoriteFragmentToDetailUserFragment(user)
                    findNavController().navigate(action)
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