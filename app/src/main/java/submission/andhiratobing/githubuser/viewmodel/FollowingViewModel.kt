package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.data.repositories.remote.FollowingRepository
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel
@Inject constructor(
    private val followingRepository: FollowingRepository
) : ViewModel() {

    private val followingMutableLiveData = MutableLiveData<String>()

    fun getFollowing(): LiveData<PagingData<UserResponseItem>> =
        followingMutableLiveData.switchMap { username ->
            followingRepository.following(username).cachedIn(viewModelScope)
        }

    fun setFollowing(username: String) {
        this.followingMutableLiveData.value = username
    }


}