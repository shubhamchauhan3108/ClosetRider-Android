package com.arramton.closet.restService

import android.app.Application
import com.arramton.closet.rider.restService.ApiInterface
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

class RetrofitBuilder(application: Application) {
    private val retrofit: Retrofit

    init {

        val okHttpClient: OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    Interceptor { chain: Interceptor.Chain ->
                        val original = chain.request()
                        val requestBuilder: Request.Builder = original.newBuilder()
                            .addHeader("Access-Control-Allow-Origin", "*")
                            .addHeader("Access-Control-Allow-Methods", "GET,POST,PUT, OPTIONS")
                            .method(original.method, original.body)
                        val request: Request = requestBuilder.build()
                        chain.proceed(request)
                    }
                )
                .addInterceptor(ChuckerInterceptor(application))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .build()
        val gson = GsonBuilder().setLenient().create()
        retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    public val api: ApiInterface
        get() = retrofit.create(ApiInterface::class.java)

    companion object {
//                const val BASEURL = "http://13.234.225.218:5505/"
       //         const val BASEURL = "http://3.6.83.195:2234/"
                const val BASEURL = "https://admin.tsparkmanagement.com/"
//        const val BASEURL = "https://a2ca-2409-4050-e33-ea39-64f2-b06f-cf9a-ba1e.ngrok-free.app/"
        private var mInstance: RetrofitBuilder? = null

        @Synchronized
        public fun getInstance(application: Application): RetrofitBuilder? {
            if (mInstance == null) {
                mInstance = RetrofitBuilder(application)
            }
            return mInstance
        }
    }
}