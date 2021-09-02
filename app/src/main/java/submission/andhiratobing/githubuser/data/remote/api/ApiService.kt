package submission.andhiratobing.githubuser.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import submission.andhiratobing.githubuser.data.remote.responses.detailusers.DetailUserResponse
import submission.andhiratobing.githubuser.data.remote.responses.repos.ReposResponse
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponse
import submission.andhiratobing.githubuser.data.remote.responses.users.UserResponseItem
import submission.andhiratobing.githubuser.util.Constants.Companion.ACCEPT_VERSION
import submission.andhiratobing.githubuser.util.Constants.Companion.API_GITHUB_KEY

interface ApiService {

    @GET("search/users")
    @Headers("Accept: $ACCEPT_VERSION", "Authorization: token $API_GITHUB_KEY")
    suspend fun searchUsers(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): UserResponse


    @GET("users/{username}")
    @Headers("Accept: $ACCEPT_VERSION", "Authorization: token $API_GITHUB_KEY")
    fun detailUsers(
        @Path("username") username: String,
    ): Call<DetailUserResponse>


    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_GITHUB_KEY")
    suspend fun getFollowers(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UserResponseItem>


    @GET("users/{username}/following")
    @Headers("Accept: $ACCEPT_VERSION", "Authorization: token $API_GITHUB_KEY")
    suspend fun getFollowing(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<UserResponseItem>


    @GET("users/{username}/repos")
    @Headers("Accept: $ACCEPT_VERSION", "Authorization: token $API_GITHUB_KEY")
    suspend fun getRepository(
        @Path("username") username: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): List<ReposResponse>
}