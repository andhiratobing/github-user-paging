# Dokumentasi API
 - https://docs.github.com/en/rest
 
## BASE_URL
  ``` https://api.github.com/ ```

## Request:

- Method : GET 
- Header :
  - Accept: `application/json`
  - Authorization : `YOUR_TOKEN_GITHUB`
    

    ### Search user
    - Endpoint : `search/users`
    - Example :
        ```
        @GET("search/users")
        @Headers("Accept: $ACCEPT_VERSION", "Authorization: token$YOUR_TOKEN_GITHUB")
        suspend fun searchUsers(
            @Query("q") query: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
        ): UserResponse
        
        ```  
    
    ### Detail user
    - Endpoint : `users/{username}`
    - Example :
        ```
        @GET("search/users")
        @Headers("Accept: $ACCEPT_VERSION", "Authorization: token $YOUR_TOKEN_GITHUB")
        fun detailUsers(
            @Path("username") username: String,
        ): Call<DetailUserResponse>
        ```
    ### Followers
    - Endpoint : `users/{username}/followers`
    - Example : 
        ```
        @GET("users/{username}/followers")
        @Headers("Authorization: token $YOUR_TOKEN_GITHUB")
        suspend fun getFollowers(
            @Path("username") username: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
        ): List<UserResponseItem>

    ### Following
    - Endpoint : `users/{username}/following`
    - Example :    
        ```
        @GET("users/{username}/following")
        @Headers("Authorization: token $YOUR_TOKEN_GITHUB")
        suspend fun getFollowing(
            @Path("username") username: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
        ): List<UserResponseItem>

    ### Repositories
    - Endpoint : `users/{username}/repos`
    - Example :   
        ```
        @GET("users/{username}/repos")
        @Headers("Authorization: token $YOUR_TOKEN_GITHUB")
        suspend fun getRepos(
            @Path("username") username: String,
            @Query("page") page: Int,
            @Query("per_page") perPage: Int,
        ): List<ReposResponse>

