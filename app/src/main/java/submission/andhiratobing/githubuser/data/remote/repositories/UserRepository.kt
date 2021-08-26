package submission.andhiratobing.githubuser.data.remote.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.paging.UserPagingSource
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.remote.responses.searchusers.UserResponseItem
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_INITIALLOADSIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_MAX_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PAGE_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PREFETCHDISTACE
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class UserRepository
@Inject constructor(private val apiService: ApiService) {

    val detailUserMutableLiveData = MutableLiveData<DetailUserResponse>()

    fun searchUsers(query: String): LiveData<PagingData<UserResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                maxSize = NETWORK_MAX_SIZE,
                enablePlaceholders = false,
                prefetchDistance = NETWORK_PREFETCHDISTACE,
                initialLoadSize = NETWORK_INITIALLOADSIZE
            ),
            pagingSourceFactory = { UserPagingSource(apiService, query) }
        ).liveData
    }


    fun detailUser(login: String) {
        apiService.detailUsers(login).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                detailUserMutableLiveData.postValue(response.body())
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                t.message?.let { Log.e("Failure", it) }
            }
        })
    }
}