package submission.andhiratobing.githubuser.data.remote.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReposRepository @Inject constructor(
    private val apiService: ApiService
) {

    private var _reposMutableLiveData = MutableLiveData<List<ReposResponse>>()
    val reposLiveData: LiveData<List<ReposResponse>> get() = _reposMutableLiveData


    fun repos(username: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                apiService.getRepository(username)
                    .enqueue(object : Callback<List<ReposResponse>> {
                        override fun onResponse(
                            call: Call<List<ReposResponse>>,
                            response: Response<List<ReposResponse>>,
                        ) {
                            if (response.isSuccessful) {
                                _reposMutableLiveData.postValue(response.body())
                            }
                        }

                        override fun onFailure(call: Call<List<ReposResponse>>, t: Throwable) {
                            t.message?.let { Log.e("Failure", it) }
                        }

                    })
            }
        } catch (e: HttpException) {
            e.printStackTrace()
        }
    }

}