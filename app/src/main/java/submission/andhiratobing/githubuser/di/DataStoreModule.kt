package submission.andhiratobing.githubuser.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.createDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import submission.andhiratobing.githubuser.data.repositories.datastore.DataStoreRepository
import submission.andhiratobing.githubuser.util.Constants.Companion.DATA_STORE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun dataStoreRepository(@ApplicationContext context: Context): DataStoreRepository =
        DataStoreRepository(context)


    @Provides
    @Singleton
    fun dataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        appContext.createDataStore(DATA_STORE_NAME)

}