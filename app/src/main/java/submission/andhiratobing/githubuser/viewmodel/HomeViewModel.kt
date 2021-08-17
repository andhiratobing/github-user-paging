package submission.andhiratobing.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import submission.andhiratobing.githubuser.data.model.User
import submission.andhiratobing.githubuser.data.model.UserEntity
import submission.andhiratobing.githubuser.util.ParseJson.getJSONFromAssets

class HomeViewModel constructor(application: Application) :
    AndroidViewModel(application) {

    private val _listData: MutableLiveData<List<UserEntity>> = MutableLiveData()
    val listData: LiveData<List<UserEntity>>
        get() = _listData


    private fun fetchDataDummy(): List<UserEntity> {
        val jsonFile = getJSONFromAssets(getApplication())
        val dataUsers = Gson().fromJson(jsonFile, User::class.java)
        return dataUsers.users
    }

    init {
        _listData.value = fetchDataDummy()
    }
}