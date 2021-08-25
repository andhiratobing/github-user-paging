package submission.andhiratobing.githubuser.data.local.repositories

import androidx.lifecycle.LiveData
import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepository @Inject constructor(
    private val favoriteUserDao: FavoriteUserDao,
) {


    suspend fun addFavoriteUser(data: FavoriteEntity) {
        favoriteUserDao.addFavoriteUser(data)
    }

    fun getAllFavoriteUser() : LiveData<List<FavoriteEntity>>{
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