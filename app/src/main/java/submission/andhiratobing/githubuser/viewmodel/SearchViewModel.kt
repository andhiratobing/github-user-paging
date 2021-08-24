package submission.andhiratobing.githubuser.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import submission.andhiratobing.githubuser.data.remote.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val search = currentQuery.switchMap { queryString ->
        userRepository.setSearchUsers(queryString).cachedIn(viewModelScope)
    }

    fun getSearchUser(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }


}





