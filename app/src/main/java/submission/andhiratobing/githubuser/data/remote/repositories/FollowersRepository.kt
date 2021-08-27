package submission.andhiratobing.githubuser.data.remote.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.followers.FollowersResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowersRepository
@Inject constructor(
    private val apiService: ApiService,
) {
    private var _followersMutableLiveData = MutableLiveData<List<FollowersResponse>>()
    val followersLiveData: LiveData<List<FollowersResponse>> get() = _followersMutableLiveData


    fun followers(username: String) {
        try {
            apiService.getFollowers(username)
                .enqueue(object : Callback<List<FollowersResponse>> {
                    override fun onResponse(
                        call: Call<List<FollowersResponse>>,
                        response: Response<List<FollowersResponse>>,
                    ) {
                        if (response.isSuccessful) {
                            _followersMutableLiveData.postValue(response.body())
                        }
                    }

                    override fun onFailure(call: Call<List<FollowersResponse>>, t: Throwable) {
                        t.message?.let { Log.e("Failure", it) }
                    }

                })
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }
}