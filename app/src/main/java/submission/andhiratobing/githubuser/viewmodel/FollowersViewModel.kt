package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.responses.followers.FollowersResponse
import submission.andhiratobing.githubuser.data.repositories.remote.FollowersRepository
import submission.andhiratobing.githubuser.util.network.NetworkState
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val followersRepository: FollowersRepository
) : ViewModel() {

    fun setNetworkState(): LiveData<NetworkState> = followersRepository.networkState

    fun setFollowers(): LiveData<List<FollowersResponse>> = followersRepository.followersLiveData


    fun getFolloers(username: String) {
        viewModelScope.launch {
            followersRepository.followers(username)
        }
    }
}