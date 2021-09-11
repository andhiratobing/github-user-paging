package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.repositories.remote.UserRepository
import submission.andhiratobing.githubuser.util.state.ResourceState
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class DetailUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    fun setNetworkState(): LiveData<ResourceState> = userRepository.networkState

    fun setDetailUsers(): LiveData<DetailUserResponse> {
        return userRepository.detailUserLiveData
    }

    fun getDetailUsers(username: String) {
        viewModelScope.launch {
            userRepository.detailUser(username)
        }
    }

}
