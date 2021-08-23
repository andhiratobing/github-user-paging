//package submission.andhiratobing.githubuser.data.local.dao
//
//import androidx.lifecycle.LiveData
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import submission.andhiratobing.githubuser.data.local.entities.UserFavorite
//
//@Dao
//interface FavoriteUserDao {
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun addFavoriteUser(user: UserFavorite)
//
//    @Query("SELECT * FROM favorite_users ORDER BY username ASC")
//    fun getFavoriteUser(): LiveData<List<UserFavorite>>
//
//    @Query("SELECT COUNT(*) FROM favorite_users")
//    suspend fun getCountUsers(): Int
//
//    @Query("SELECT COUNT(*) FROM favorite_users WHERE username = :username")
//    suspend fun getCountFavorite(username: String): Int
//
//    @Query("DELETE FROM favorite_users WHERE username = :username ")
//    suspend fun deleteFavoriteUser(username: String): Int
//}