package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.data.local.repositories.FavoriteRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {


    fun getAllFavoriteUser(): LiveData<List<FavoriteEntity>> {
        return favoriteRepository.getAllFavoriteUser()
    }

    suspend fun getCountUsers() = favoriteRepository.getCountUsers()

    /*Favorite*/

    fun addFavoriteUser(
        id: Int,
        username: String,
        name: String,
        avatar: String,
        company: String?,
        bio: String?,
        location: String?,
        following: Int,
        followers: Int,
        repository: Int)
    {
        viewModelScope.launch {
            favoriteRepository.addFavoriteUser(
                FavoriteEntity(
                    id,
                    username,
                    name,
                    avatar,
                    company,
                    bio,
                    location,
                    following,
                    followers,
                    repository)
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