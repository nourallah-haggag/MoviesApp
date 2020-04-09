package com.meetntrain.moviesapp.common.di

import com.meetntrain.moviesapp.common.Constants
import com.meetntrain.moviesapp.common.networking.MoviesApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

// module is a DSL function used for specifying dependencies
val networkModule = module {
    // we will define the network dependency with the "single" DSL function as we only need one instance throughout the app
    single(named(Constants.BASE_URL_KOIN_KEY)) {
        // the named parameter allows us to create multiple instances of an object with different names(optional)
        Constants.BASE_URL
    }

    // provide the image server url
    single(named(Constants.IMAGE_SERVER_KOIN_KEY)) {
        Constants.IMAGE_SERVER
    }

    // provides http logging interceptor to be used in the okhttpclient
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        interceptor
    }

    // provides an interceptor for adding auth header
    single(named(Constants.AUTH_INTERCEPTOR_KOIN_KEY)) {
        // create an anonymous class that extends the Interceptor class of OKHTTP and add the auth header in the intercepted request
       val interceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val request = chain.request().newBuilder()
                    .addHeader(Constants.AUTH_HEADER_KEY, Constants.API_KEY)
                    .build()
                return chain.proceed(request)
            }

        }
        interceptor
    }

    single {
        val client = OkHttpClient().newBuilder()
        Constants.NETWORK_TIMEOUT.apply {
            client
                .connectTimeout(this, TimeUnit.SECONDS)
                .readTimeout(this, TimeUnit.SECONDS)
                .writeTimeout(this, TimeUnit.SECONDS)
        }
        // add interceptor only if we are in the debug build
        if (BuildConfig.DEBUG) {
            client.addInterceptor(interceptor = get<HttpLoggingInterceptor>())
        }
        client.addInterceptor(interceptor = get(named(name = Constants.AUTH_INTERCEPTOR_KOIN_KEY)))
        client.build()
    }

    // provide moshi for parsing JSON
    single {
        Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder().baseUrl(get<String>(named(Constants.BASE_URL_KOIN_KEY)))
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
    }

    single {
        // create the networking interface
        get<Retrofit>().create(MoviesApi::class.java)
    }


}