package submission.andhiratobing.githubuser.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import submission.andhiratobing.githubuser.data.model.UserEntity

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteUser(user: UserEntity)

    @Query("SELECT * FROM favorite_users ORDER BY username ASC")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM favorite_users WHERE username = :username")
    suspend fun getCountFavorite(username: String): Int

    @Query("DELETE FROM favorite_users WHERE username = :username ")
    suspend fun deleteFavoriteUser(username: String): Int
}