package com.example.simplegithubconsumer.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.simplegithubconsumer.data.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.simplegithubconsumer.data.FavoriteUser
import com.example.simplegithubconsumer.data.MappingHelper
import kotlinx.coroutines.launch


class FavoriteViewModel constructor(application: Application) : AndroidViewModel(application) {

    private val _listFavoriteUser = MutableLiveData<ArrayList<FavoriteUser>>()


    fun setListFavoriteUser(context: Context) {
        viewModelScope.launch {
            val cursor = context.contentResolver.query(
                CONTENT_URI,
                null,
                null,
                null,
                null
            )
            val mapper = MappingHelper.cursorToList(cursor)
            _listFavoriteUser.postValue(mapper)
        }
    }

    fun getListFavoriteUser(): LiveData<ArrayList<FavoriteUser>> {
        return _listFavoriteUser
    }


}