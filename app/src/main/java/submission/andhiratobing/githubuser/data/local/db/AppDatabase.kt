package submission.andhiratobing.githubuser.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
import submission.andhiratobing.githubuser.data.local.dao.provider.FavoriteUserProviderDao
import submission.andhiratobing.githubuser.data.local.dao.widget.FavoriteUserWidgetDao
import submission.andhiratobing.githubuser.data.local.entities.FavoriteEntity
import submission.andhiratobing.githubuser.util.Constants.Companion.DATABASE_NAME

@Database(
    entities =
    [FavoriteEntity::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteUserDao(): FavoriteUserDao
    abstract fun favoriteUserWidgetDao(): FavoriteUserWidgetDao
    abstract fun favoriteUserProvideDao(): FavoriteUserProviderDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .build()
                    .also { INSTANCE = it }
            }

    }
}