package submission.andhiratobing.githubuser.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import submission.andhiratobing.githubuser.data.local.entities.UserEntity

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteUser(user: UserEntity)

    @Query("SELECT * FROM favorite_users ORDER BY id ASC")
    fun getAllFavoriteUser(): LiveData<List<UserEntity>>

    @Query("SELECT COUNT(*) FROM favorite_users")
    suspend fun getCountUsers(): Int

    @Query("SELECT COUNT(*) FROM favorite_users WHERE id = :id")
    suspend fun getCountFavorite(id : Int): Int

    @Query("DELETE FROM favorite_users WHERE id = :id ")
    suspend fun deleteFavoriteUser(id: Int): Int
}