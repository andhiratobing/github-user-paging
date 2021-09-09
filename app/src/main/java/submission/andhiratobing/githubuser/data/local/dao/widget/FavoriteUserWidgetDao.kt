package submission.andhiratobing.githubuser.data.local.dao.widget

import androidx.room.Dao
import androidx.room.Query
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity

@Dao
interface FavoriteUserWidgetDao {

    @Query("SELECT * FROM favorite_users")
    fun getAllDataToFavoriteWidget(): List<FavoriteEntity>
}