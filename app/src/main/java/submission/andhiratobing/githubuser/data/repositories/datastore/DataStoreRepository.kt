package submission.andhiratobing.githubuser.data.repositories.datastore

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.createDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import submission.andhiratobing.githubuser.data.repositories.datastore.DataStoreRepository.PreferencesKey.REMINDER_REPEATING
import submission.andhiratobing.githubuser.data.repositories.datastore.DataStoreRepository.PreferencesKey.THEMES
import submission.andhiratobing.githubuser.util.Constants.Companion.DATA_STORE_NAME
import submission.andhiratobing.githubuser.util.Constants.Companion.REMINDER_REPEATING_KEY
import submission.andhiratobing.githubuser.util.Constants.Companion.THEMES_KEY
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DataStoreRepository @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore = context.createDataStore(DATA_STORE_NAME)

    private object PreferencesKey {
        val THEMES = intPreferencesKey(THEMES_KEY)
        val REMINDER_REPEATING = booleanPreferencesKey(REMINDER_REPEATING_KEY)

    }

    val themesFlow: Flow<Int> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[THEMES] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

    val reminderFLow: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                exception.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            preferences[REMINDER_REPEATING] ?: false
        }


    suspend fun setThemes(themesValue: Int) {
        dataStore.edit { preferences ->
            preferences[THEMES] = themesValue
        }
    }

    suspend fun saveReminderRepeating(state: Boolean) {
        dataStore.edit { preferences ->
            preferences[REMINDER_REPEATING] = state
        }

    }
}