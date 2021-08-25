package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.local.entities.UserEntity
import submission.andhiratobing.githubuser.data.local.repositories.FavoriteRepository
import submission.andhiratobing.githubuser.data.remote.repositories.UserRepository
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class DetailUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {


    fun setDetailUsers(): LiveData<DetailUserResponse> {
        return userRepository.detailUserMutableLiveData
    }

    fun getDetailUsers(username: String) {
        viewModelScope.launch {
            userRepository.setDetailUser(username)
        }
    }


    /*Favorite*/

    fun addFavoriteUser(id: Int, username: String, avatar: String) {
        viewModelScope.launch {
            favoriteRepository.addFavoriteUser(
                UserEntity(id, username, avatar)
            )
        }
    }

    suspend fun getCountFavoriteUsers(id: Int): Int = favoriteRepository.getCountFavoriteUsers(id)

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            favoriteRepository.deleteFavoriteUser(id)
        }
    }

}
