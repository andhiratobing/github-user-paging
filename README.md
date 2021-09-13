# Demo
https://user-images.githubusercontent.com/43465830/133141095-7dfde779-9bab-4f95-8488-0bf4190b1330.mp4

# API Documentation
- https://docs.github.com/en/rest

## BASE_URL
  ```
  https://api.github.com/
  ```

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
        @GET("users/{username}")
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

    ```

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
    ```

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
    ```

# How to clone

- Syntax :

  ```git clone -b <NAMA_BRANCH>  <GIT_REMOTE_URL> ```

- Master
    ```
    git clone -b master https://github.com/andhiratobing/github-user-paging
    ```
- Submission 1
    ```
    git clone -b submission_1 https://github.com/andhiratobing/github-user-paging/tree/submission_1
    ```
- Submission 2
    ```
    git clone -b submission_2 https://github.com/andhiratobing/github-user-paging/tree/submission_2
    ```
- Submission 3
    ```
    git clone -b submission_3 https://github.com/andhiratobing/github-user-paging/tree/submission_3
    ```

## MAD Scorecard
![](https://github.com/andhiratobing/github-user-paging/blob/master/app/src/main/assets/summary.png)

## Dependencies

- [Material Design](https://material.io)
- [Circle Image](https://github.com/hdodenhof/CircleImageView)
- [Rounded Image](https://github.com/vinc3m1/RoundedImageView)
- [Glide](https://github.com/bumptech/glide)
- [Lottie animation](https://lottiefiles.com)
- [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started)
- [Retrofit2](https://square.github.io/retrofit)
- [OkHttp](https://square.github.io/okhttp)
- [Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor)
- [GSON](https://github.com/google/gson)
- [Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle)
- [Delegate activity](https://developer.android.com/jetpack/androidx/releases/activity)
- [Kotlin coroutine on Android](https://developer.android.com/kotlin/coroutines)
- [Kotlin flow on Android](https://developer.android.com/kotlin/flow)
- [Room Database](https://developer.android.com/jetpack/androidx/releases/room?gclid=EAIaIQobChMIh8PguPD68gIVTgwrCh1l9wL_EAAYASAAEgKh6PD_BwE&gclsrc=aw.ds)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Datastore preferences](https://developer.android.com/topic/libraries/architecture/datastore)
- [Preference](https://developer.android.com/reference/android/preference/Preference)

# LICENSE

                                 Apache License
                           Version 2.0, January 2004
                        http://www.apache.org/licenses/


    Copyright © 2021 Andhi Ratobing

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

