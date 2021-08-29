package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.data.repositories.remote.FollowingRepository
import submission.andhiratobing.githubuser.util.network.NetworkState
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel
@Inject constructor(
    private val followingRepository: FollowingRepository
) : ViewModel() {

    fun setNetworkState(): LiveData<NetworkState> = followingRepository.networkState

    fun setFollowing(): LiveData<List<FollowingResponse>> = followingRepository.followingLiveData


    fun getFollowing(username: String) {
        viewModelScope.launch {
            followingRepository.following(username)
        }
    }


}