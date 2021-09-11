package submission.andhiratobing.githubuser

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.firstOrNull
import submission.andhiratobing.githubuser.data.repositories.datastore.DataStoreRepository
import javax.inject.Inject

@HiltAndroidApp
class Application : Application() {

    @Inject
    lateinit var dataStoreRepository: DataStoreRepository

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Main).launch {
            dataStoreRepository.reminderFLow.firstOrNull()

        }

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main + SupervisorJob()) {
                dataStoreRepository.themesFlow.firstOrNull()?.let {
                    AppCompatDelegate.setDefaultNightMode(it)
                }
            }
        }
    }
}