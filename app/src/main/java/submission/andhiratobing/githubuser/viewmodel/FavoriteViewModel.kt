package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import submission.andhiratobing.githubuser.data.local.entities.UserEntity
import submission.andhiratobing.githubuser.data.local.repositories.FavoriteRepository
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {


    fun getAllFavoriteUser(): LiveData<List<UserEntity>> {
        return favoriteRepository.getAllFavoriteUser()
    }

    suspend fun getCountUsers() = favoriteRepository.getCountUsers()


}