package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.repositories.ReposRepository
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val reposRepository: ReposRepository
) : ViewModel() {

    fun setRepository(): LiveData<List<ReposResponse>> = reposRepository.reposLiveData

    fun getRepository(username: String) {
        viewModelScope.launch {
            reposRepository.repos(username)
        }
    }

}