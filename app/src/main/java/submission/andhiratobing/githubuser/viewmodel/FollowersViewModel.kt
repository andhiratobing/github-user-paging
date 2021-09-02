package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.data.repositories.remote.FollowersRepository
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(
    private val followersRepository: FollowersRepository
) : ViewModel() {


    private val followersMutableLiveData = MutableLiveData<String>()

    fun getFollowers(): LiveData<PagingData<UserResponseItem>> =
        followersMutableLiveData.switchMap { username ->
            followersRepository.followers(username).cachedIn(viewModelScope)
        }

    fun setFollowers(username: String) {
        this.followersMutableLiveData.value = username
    }
}
