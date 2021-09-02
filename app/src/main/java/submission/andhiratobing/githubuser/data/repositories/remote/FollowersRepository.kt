package submission.andhiratobing.githubuser.data.repositories.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import submission.andhiratobing.githubuser.adapter.remote.paging.followersusers.FollowersPagingSource
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_INITIAL_LOAD_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PAGE_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PRE_FETCH_DISTACE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowersRepository
@Inject constructor(
    private val apiService: ApiService
) {

    fun followers(username: String): LiveData<PagingData<UserResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = NETWORK_PRE_FETCH_DISTACE,
                initialLoadSize = NETWORK_INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { FollowersPagingSource(apiService, username) }
        ).liveData
    }
}
