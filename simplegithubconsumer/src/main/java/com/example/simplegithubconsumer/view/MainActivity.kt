package com.example.simplegithubconsumer.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simplegithubconsumer.adapter.FavoriteAdapter
import com.example.simplegithubconsumer.databinding.ActivityMainBinding
import com.example.simplegithubconsumer.viewmodel.FavoriteViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val favoriteViewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initFavoriteViewModel()
        setDataListFavoriteUser()
        swipeRefrehLayout()

    }


    private fun setDataListFavoriteUser() {
        favoriteViewModel.setListFavoriteUser(this)
    }

    private fun initFavoriteViewModel() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main) {
                favoriteViewModel.getListFavoriteUser().observe(this@MainActivity, {
                    it.let {
                        if (it.isNotEmpty()) {
                            favoriteAdapter.submitList(it)
                            binding.apply {
                                ivEmptyData.visibility = View.GONE
                                tvEmptyData.visibility = View.GONE
                            }
                        } else {
                            binding.apply {
                                ivEmptyData.visibility = View.VISIBLE
                                tvEmptyData.visibility = View.VISIBLE
                            }
                        }
                    }
                })
            }
        }
    }

    private fun swipeRefrehLayout() {
        binding.apply {
            swipeRefreshLayout.setOnRefreshListener {
                setDataListFavoriteUser()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initAdapter() {
        binding.apply {
            favoriteAdapter = FavoriteAdapter()
            rvUser.layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            rvUser.adapter = favoriteAdapter
            rvUser.setHasFixedSize(true)

        }
    }
}