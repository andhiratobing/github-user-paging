package submission.andhiratobing.githubuser.data.local.dao.provider

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavoriteUserProviderDao  {

    @Query("SELECT * FROM favorite_users")
    fun getDataFavoriteForProvider(): Cursor

}