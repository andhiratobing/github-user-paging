package submission.andhiratobing.githubuser.data.repositories.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.followers.FollowersResponse
import submission.andhiratobing.githubuser.util.network.NetworkState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowersRepository
@Inject constructor(
    private val apiService: ApiService,
) {

    //network handle
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState> get() = _networkState

    private var _followersMutableLiveData = MutableLiveData<List<FollowersResponse>>()
    val followersLiveData: LiveData<List<FollowersResponse>> get() = _followersMutableLiveData


    fun followers(username: String) {
        _networkState.postValue(NetworkState.LOADING)
        try {
            apiService.getFollowers(username)
                .enqueue(object : Callback<List<FollowersResponse>> {
                    override fun onResponse(
                        call: Call<List<FollowersResponse>>,
                        response: Response<List<FollowersResponse>>,
                    ) {
                        if (response.isSuccessful) {
                            _networkState.postValue(NetworkState.LOADED)
                            _followersMutableLiveData.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable) {
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