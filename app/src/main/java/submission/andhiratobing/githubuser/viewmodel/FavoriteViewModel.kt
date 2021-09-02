package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.repositories.local.FavoriteRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {


    fun getAllFavoriteUser(): LiveData<List<FavoriteEntity>> {
        return favoriteRepository.getAllFavoriteUser()
    }

    suspend fun getCountUsers(): Int {
        return favoriteRepository.getCountUsers()
    }

    fun addFavoriteUser(detailUserResponse: DetailUserResponse) {
        viewModelScope.launch {
            favoriteRepository.addFavoriteUser(
                detailUserResponse.id,
                detailUserResponse.username,
                detailUserResponse.name,
                detailUserResponse.avatar,
                detailUserResponse.company,
                detailUserResponse.bio,
                detailUserResponse.location,
                detailUserResponse.following,
                detailUserResponse.followers,
                detailUserResponse.repository
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