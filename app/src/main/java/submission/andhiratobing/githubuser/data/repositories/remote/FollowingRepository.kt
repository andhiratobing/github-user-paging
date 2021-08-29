package submission.andhiratobing.githubuser.data.repositories.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.util.network.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingRepository
@Inject constructor(
    private val apiService: ApiService,
) {

    //network handle
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    private var _followingMutableLiveData = MutableLiveData<List<FollowingResponse>>()
    val followingLiveData: LiveData<List<FollowingResponse>> get() = _followingMutableLiveData


    fun following(username: String) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            apiService.getFollowing(username)
                .enqueue(object : Callback<List<FollowingResponse>> {
                    override fun onResponse(
                        call: Call<List<FollowingResponse>>,
                        response: Response<List<FollowingResponse>>,
                    ) {
                        if (response.isSuccessful) {
                            _networkState.postValue(NetworkState.LOADED)
                            _followingMutableLiveData.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                        _networkState.postValue(NetworkState.FAILED)
                        t.message?.let { Log.e("Failure", it) }
                    }

                })
        } catch (e: HttpException) {
            e.printStackTrace()
            e.message?.let { Log.e("Failure", it) }
        }
    }
}