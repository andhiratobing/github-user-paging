package submission.andhiratobing.githubuser.view.fragments.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import submission.andhiratobing.githubuser.adapter.UserAdapter
import submission.andhiratobing.githubuser.data.model.UserEntity
import submission.andhiratobing.githubuser.databinding.FragmentHomeBinding
import submission.andhiratobing.githubuser.view.activities.DetailUserActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userAdapter: UserAdapter
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        initHomeViewModel()
    }


    private fun initHomeViewModel() {
        homeViewModel.listData.observe(viewLifecycleOwner, {
            if (it != null) {
                userAdapter.setListDataUser(it as ArrayList<UserEntity>)
            }
        })
    }

    private fun sendDataToDetailFragment(data: UserEntity) {
        val intent = Intent(requireContext(), DetailUserActivity::class.java)
        intent.putExtra(DetailUserActivity.DATA_USER, data)
        startActivity(intent)
    }

    private fun initRecyclerView() {
        binding.apply {
            userAdapter = UserAdapter()
            rvUser.layoutManager = LinearLayoutManager(requireActivity())
            rvUser.adapter = userAdapter
            rvUser.setHasFixedSize(true)

            //Click item
            userAdapter.setOnItemClickCallBack(object : UserAdapter.OnItemClickCallBack {
                override fun onItemClick(data: UserEntity) {
                    sendDataToDetailFragment(data)
                }
            })

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}