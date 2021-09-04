package submission.andhiratobing.githubuser.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import submission.andhiratobing.githubuser.data.local.dao.FavoriteUserDao
import submission.andhiratobing.githubuser.data.local.db.AppDatabase
import submission.andhiratobing.githubuser.util.Constants.Companion.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext application: Context) =
        Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME).build()


    @Provides
    @Singleton
    fun provideFavoriteDao(appDatabase: AppDatabase): FavoriteUserDao {
        return appDatabase.favoriteUserDao()
    }
}