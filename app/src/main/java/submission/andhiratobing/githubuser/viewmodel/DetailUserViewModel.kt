package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.repositories.UserRepository
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class DetailUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {



    fun getDetailUsers(login: String) {
        viewModelScope.launch {
            userRepository.setDetailUser(login)
        }
    }


}


//private var favoriteUserDao: FavoriteUserDao?
//private var appDatabase: AppDatabase? = AppDatabase.getDatabase(application)
//
//init {
//    favoriteUserDao = appDatabase?.favoriteUserDao()
//}
//
//fun addFavorite(
//    username: String,
//    name: String,
//    avatar_url: String,
//    company: String,
//    location: String,
//    repository: Int,
//    follower: Int,
//    following: Int) {
//    CoroutineScope(Dispatchers.IO).launch {
//        val user = UserFavorite(
//            username,
//            name,
//            avatar_url,
//            company,
//            location,
//            repository,
//            follower,
//            following
//        )
//        favoriteUserDao?.addFavoriteUser(user)
//    }
//}
//
//
//suspend fun getCountFavorite(username: String) = favoriteUserDao?.getCountFavorite(username)
//
//fun deleteFavorite(username: String) {
//    CoroutineScope(Dispatcherzs.IO).launch {
//        favoriteUserDao?.deleteFavoriteUser(username)
//    }
//}