package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.repositories.FollowingRepository
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel
@Inject constructor(
    private val followingRepository: FollowingRepository,
) : ViewModel() {


    fun setFollowing(): LiveData<List<FollowingResponse>> {
        return followingRepository.followingMutableLiveData
    }

    fun getFollowing(username: String) {
        viewModelScope.launch {
            followingRepository.following(username)
        }
    }


}