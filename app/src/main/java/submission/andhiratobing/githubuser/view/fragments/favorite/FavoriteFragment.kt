package submission.andhiratobing.githubuser.view.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import submission.andhiratobing.githubuser.databinding.FragmentFavoriteBinding

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
//    private lateinit var favoriteAdapter: FavoriteAdapter
//    private val favoriteViewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initRecyclerView()
//        initViewModelFavorite()
//        countFavoriteUser()
    }

//
//    private fun sendDataToDetailFragment(data: UserFavorite) {
//        val intent = Intent(requireContext(), DetailUserActivity::class.java)
//        intent.putExtra(DetailUserActivity.DATA_USER, data)
//        startActivity(intent)
//    }
//
//    private fun countFavoriteUser() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val count = favoriteViewModel.getCountUser()
//            withContext(Dispatchers.Main) {
//                if (count != null) {
//                    if (count > 0){
//                        "($count users)".also { binding.tvCountFavorite.text = it }
//                        binding.tvCountFavorite.visibility = View.VISIBLE
//                    }else{
//                        binding.tvCountFavorite.visibility = View.GONE
//                    }
//                }
//            }
//        }
//    }
//
//    private fun initRecyclerView() {
//        binding.apply {
//            favoriteAdapter = FavoriteAdapter()
//            rvFavorite.layoutManager = LinearLayoutManager(requireActivity())
//            rvFavorite.adapter = favoriteAdapter
//            rvFavorite.setHasFixedSize(true)
//            //Click item
//            favoriteAdapter.setOnItemClickCallBack(object : FavoriteAdapter.OnItemClickCallBack {
//                override fun onItemClick(data: UserFavorite) {
//                    sendDataToDetailFragment(data)
//                }
//            })
//
//        }
//    }
//
//    private fun initViewModelFavorite() {
//        CoroutineScope(Dispatchers.IO).launch {
//            withContext(Dispatchers.Main) {
//                favoriteViewModel.getFavoriteUser()?.observe(viewLifecycleOwner, {
//                    if (it != null) {
//                        favoriteAdapter.setListDataUser(it as ArrayList<UserFavorite>)
//                    }
//                })
//            }
//        }
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}