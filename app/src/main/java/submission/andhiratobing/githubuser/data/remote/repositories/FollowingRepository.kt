package submission.andhiratobing.githubuser.data.remote.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FollowingRepository
@Inject constructor(
    private val apiService: ApiService,
) {

    val followingMutableLiveData = MutableLiveData<List<FollowingResponse>>()

    fun following(username: String) {
        apiService.getFollowing(username).enqueue(object : Callback<List<FollowingResponse>> {
            override fun onResponse(
                call: Call<List<FollowingResponse>>,
                response: Response<List<FollowingResponse>>,
            ) {
                if (response.isSuccessful) {
                    followingMutableLiveData.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<List<FollowingResponse>>, t: Throwable) {
                t.message?.let { Log.e("Failure", it) }
            }

        })
    }
}