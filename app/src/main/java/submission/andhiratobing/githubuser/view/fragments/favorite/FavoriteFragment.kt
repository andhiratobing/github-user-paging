package submission.andhiratobing.githubuser.view.fragments.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import submission.andhiratobing.githubuser.adapter.FavoriteAdapter
import submission.andhiratobing.githubuser.data.model.UserEntity
import submission.andhiratobing.githubuser.databinding.FragmentFavoriteBinding
import submission.andhiratobing.githubuser.view.activities.detailuser.DetailUserActivity
import submission.andhiratobing.githubuser.viewmodel.FavoriteViewModel

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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


    private fun sendDataToDetailFragment(data: UserEntity) {
        val intent = Intent(requireContext(), DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.DATA_USER, data)
        startActivity(intent)
    }

    private fun countFavoriteUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteViewModel.getCountUser()
            withContext(Dispatchers.Main) {
                "($count users)".also { binding.tvCountFavorite.text = it }
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
                override fun onItemClick(data: UserEntity) {
                    sendDataToDetailFragment(data)
                }
            })

        }
    }

    private fun initViewModelFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                favoriteViewModel.getFavoriteUser().observe(viewLifecycleOwner, {
                    if (it != null) {
                        favoriteAdapter.setListDataUser(it as ArrayList<UserEntity>)
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