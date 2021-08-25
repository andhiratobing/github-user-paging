package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import submission.andhiratobing.githubuser.data.remote.repositories.UserRepository
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import javax.inject.Inject


@HiltViewModel
@ExperimentalCoroutinesApi
class DetailUserViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {


    fun setDetailUsers(): LiveData<DetailUserResponse> {
        return userRepository.detailUserMutableLiveData
    }

    fun getDetailUsers(username: String) {
        viewModelScope.launch {
            userRepository.detailUser(username)
        }
    }

}
