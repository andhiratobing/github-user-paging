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

//    private var currentQueryValue: String? = null
//    private var currentSearchResult: Flow<PagingData<UserResponseItem>>? = null
//
//    fun getSearchUser(query: String, shouldForceRefresh: Boolean = false): Flow<PagingData<UserResponseItem>> {
//        val lastResult = currentSearchResult
//        if (query == currentQueryValue && lastResult != null && !shouldForceRefresh) {
//            return lastResult
//        }
//        currentQueryValue = query
//        val newResult: Flow<PagingData<UserResponseItem>> =
//            userRepository.getSearchResults(query).cachedIn(viewModelScope)
//        currentSearchResult = newResult
//        return newResult
//    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val search = currentQuery.switchMap { queryString ->
        userRepository.getSearchResults(queryString).cachedIn(viewModelScope)
    }

    fun searchUser(query: String) {
        currentQuery.value = query
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }


}





