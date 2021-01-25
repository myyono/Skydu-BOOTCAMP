package skydu.android.instaclone.data.remote

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import skydu.android.instaclone.BuildConfig
import java.util.concurrent.TimeUnit
import skydu.android.instaclone.data.local.prefs.UserPreferences


object Networking {

    private val userPreferences = UserPreferences()

    val service: NetworkService by lazy {
        Retrofit.Builder()
            .baseUrl("https://skydu-insta-api.herokuapp.com")
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                        })
                    .addInterceptor {
                        val newRequest: Request = it.request().newBuilder()
                            .apply {
                                val token = userPreferences.getAccessToken()
                                if (token != null) {
                                    addHeader("Authorization", "Bearer $token")
                                }
                            }
                            .addHeader("Accept", "application/json")
                            .build()
                        it.proceed(newRequest)
                    }
                    .readTimeout(60.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(60.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()

            .create(NetworkService::class.java)
    }
}