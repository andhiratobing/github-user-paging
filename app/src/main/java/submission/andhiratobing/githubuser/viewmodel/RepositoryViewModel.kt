package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.data.repositories.remote.ReposRepository
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val reposRepository: ReposRepository
) : ViewModel() {

    private val reposMutableLiveData = MutableLiveData<String>()

    fun getRepository(): LiveData<PagingData<ReposResponse>> =
        reposMutableLiveData.switchMap { username ->
            reposRepository.repository(username).cachedIn(viewModelScope)
        }

    fun setRepository(username: String) {
        this.reposMutableLiveData.value = username
    }
}
