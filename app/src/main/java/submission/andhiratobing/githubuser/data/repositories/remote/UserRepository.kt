package submission.andhiratobing.githubuser.data.repositories.remote

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
import retrofit2.HttpException
import retrofit2.Response
import submission.andhiratobing.githubuser.adapter.remote.paging.searchusers.UserPagingSource
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_INITIAL_LOAD_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PAGE_SIZE
import submission.andhiratobing.githubuser.util.Constants.Companion.NETWORK_PRE_FETCH_DISTANCE
import submission.andhiratobing.githubuser.util.state.ResourceState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@ExperimentalCoroutinesApi
class UserRepository
@Inject constructor(private val apiService: ApiService) {

    //network handle
    private val _networkState = MutableLiveData<ResourceState>()
    val networkState: LiveData<ResourceState> get() = _networkState

    private var _detailUserMutableLiveData = MutableLiveData<DetailUserResponse>()
    val detailUserLiveData: LiveData<DetailUserResponse> get() = _detailUserMutableLiveData


    fun searchUsers(query: String): LiveData<PagingData<UserResponseItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = NETWORK_PRE_FETCH_DISTANCE,
                initialLoadSize = NETWORK_INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = { UserPagingSource(apiService, query) }
        ).liveData
    }


    fun detailUser(login: String) {
        try {
            _networkState.postValue(ResourceState.LOADING)
            apiService.detailUsers(login).enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _networkState.postValue(ResourceState.LOADED)
                        _detailUserMutableLiveData.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _networkState.postValue(ResourceState.FAILED)
                    t.message?.let { Log.e("Failure", it) }
                }
            })
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }
}