package submission.andhiratobing.githubuser.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import submission.andhiratobing.githubuser.data.remote.api.ApiService
import submission.andhiratobing.githubuser.util.Constants.Companion.BASE_URL
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.apply {
            retryOnConnectionFailure(false)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .followRedirects(false)
                .followSslRedirects(false)
                .cache(null)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(providesHttpLoggingInterceptor())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

}