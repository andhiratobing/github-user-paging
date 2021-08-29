package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.data.repositories.remote.ReposRepository
import submission.andhiratobing.githubuser.util.network.NetworkState
import javax.inject.Inject

@HiltViewModel
class RepositoryViewModel @Inject constructor(
    private val reposRepository: ReposRepository
) : ViewModel() {

    fun setNetworkState(): LiveData<NetworkState> = reposRepository.networkState

    fun setRepository(): LiveData<List<ReposResponse>> = reposRepository.reposLiveData

    fun getRepository(username: String) {
        viewModelScope.launch {
            reposRepository.repos(username)
        }
    }


}