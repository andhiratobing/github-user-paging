package submission.andhiratobing.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.repositories.datastore.DataStoreRepository
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(application: Application) :
    AndroidViewModel(Application()) {

    private val dataStoreRepository = DataStoreRepository(application)

    val getThemes = dataStoreRepository.themesFlow.asLiveData()
    val getReminder = dataStoreRepository.reminderFLow.asLiveData()


    fun setAppTheme(theme: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.setThemes(theme)
        }

    fun setReminder(state: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveReminderRepeating(state)
        }


}
