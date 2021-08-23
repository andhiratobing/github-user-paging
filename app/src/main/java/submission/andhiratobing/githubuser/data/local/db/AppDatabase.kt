//package submission.andhiratobing.githubuser.data.local.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.room.migration.Migration
//import androidx.sqlite.db.SupportSQLiteDatabase
//import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
//import submission.andhiratobing.githubuser.data.local.entities.UserFavorite
//
//@Database(
//    entities =
//    [UserFavorite::class],
//    version = 1, exportSchema = false
//)
//abstract class AppDatabase : RoomDatabase() {
//
//    abstract fun favoriteUserDao(): FavoriteUserDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        private val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("ALTER TABLE tb_users RENAME TO favorite_users;")
//            }
//        }
//
//        fun getDatabase(context: Context): AppDatabase =
//            INSTANCE ?: synchronized(this) {
//                INSTANCE ?: Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "github_user"
//                )
//                    .addMigrations(MIGRATION_1_2)
//                    .allowMainThreadQueries()
//                    .build()
//                    .also { INSTANCE = it }
//            }
//
//    }
//}