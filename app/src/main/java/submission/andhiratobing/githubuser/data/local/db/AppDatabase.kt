package submission.andhiratobing.githubuser.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity

@Database(
    entities =
    [FavoriteEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao

}