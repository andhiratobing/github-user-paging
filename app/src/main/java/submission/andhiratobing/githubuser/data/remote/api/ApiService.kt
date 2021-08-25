package submission.andhiratobing.githubuser.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.remote.responses.following.FollowingResponse
import submission.andhiratobing.githubuser.data.remote.responses.searchusers.UserResponse
import submission.andhiratobing.githubuser.util.Constants.Companion.API_GITHUB_KEY

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token $API_GITHUB_KEY")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserResponse


    @GET("users/{username}")
    @Headers("Authorization: token $API_GITHUB_KEY")
    fun detailUsers(
        @Path("username") username: String,
    ): Call<DetailUserResponse>


    @GET("users/{username}/following")
    @Headers("Authorization: token $API_GITHUB_KEY")
    fun getFollowing(
        @Path("username") username: String
    ): Call<List<FollowingResponse>>


    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_GITHUB_KEY")
    fun getFollowers(
        @Path("username") username: String,
    ): Call<List<UserResponse>>


    @GET("users/{username}/repos")
    @Headers("Authorization: token $API_GITHUB_KEY")
    suspend fun getRepository(
        @Path("username") username: String,
    ): Call<List<UserResponse>>

}