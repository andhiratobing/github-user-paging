package submission.andhiratobing.githubuser.data.repositories.local

import androidx.lifecycle.LiveData
import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteUserDao: FavoriteUserDao
) {


    suspend fun addFavoriteUser(
        id: Int,
        username: String,
        name: String?,
        avatar: String?,
        company: String?,
        bio: String?,
        location: String?,
        following: Int?,
        followers: Int?,
        repository: Int?
    ) {
        favoriteUserDao.addFavoriteUser(
            FavoriteEntity(
                id,
                username,
                name ?: "",
                avatar ?: "",
                company ?: "",
                bio ?: "",
                location ?: "",
                following ?: 0,
                followers ?: 0,
                repository ?: 0
            )
        )
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteEntity>> {
        return favoriteUserDao.getAllFavoriteUser()
    }

    suspend fun getCountFavoriteUsers(id: Int): Int = favoriteUserDao.getCountFavorite(id)


    suspend fun getCountUsers(): Int {
        return favoriteUserDao.getCountUsers()
    }

    suspend fun deleteFavoriteUser(id: Int) {
        favoriteUserDao.deleteFavoriteUser(id)
    }


}