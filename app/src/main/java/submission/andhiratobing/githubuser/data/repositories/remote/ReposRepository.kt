package submission.andhiratobing.githubuser.data.repositories.remote

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import submission.andhiratobing.githubuser.adapter.remote.paging.reposusers.ReposPagingSource
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.util.Constants
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PAGE_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PRE_FETCH_DISTANCE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReposRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun repository(username: String): LiveData<PagingData<ReposResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = NETWORK_PRE_FETCH_DISTANCE,
                initialLoadSize = Constants.NETWORK_INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { ReposPagingSource(apiService, username) }
        ).liveData
    }
}