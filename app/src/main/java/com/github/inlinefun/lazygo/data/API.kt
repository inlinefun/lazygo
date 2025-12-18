package com.github.inlinefun.lazygo.data

import android.content.Context
import com.github.inlinefun.lazygo.ui.Constants
import com.github.inlinefun.lazygo.util.getApiKey
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object API {

    private lateinit var routesApiInstance: Retrofit
    private lateinit var _routes: RoutingAPI
    val routes
        get() = _routes

    fun init(context: Context) {
        routesApiInstance = createRoutesAPIInstance(
            apiKey = context.getApiKey()
        )
        _routes = routesApiInstance.create<RoutingAPI>()
    }

    private fun createRoutesAPIInstance(
        apiKey: String
    ): Retrofit {
        OkHttpClient.Builder()
            .addInterceptor { interceptorChain ->
                interceptorChain.proceed(
                    request = interceptorChain
                        .request()
                        .newBuilder()
                        .header(
                            name = "Content-Type",
                            value = "application/json"
                        )
                        .header(
                            name = "X-Goog-FieldMask",
                            value = "routes.distanceMeters,routes.polyline.encodedPolyline"
                        )
                        .header(
                            name = "X-Goog-Api-Key",
                            value = apiKey
                        )
                        .build()
                )
            }
            .build()
            .let { client ->
                return Retrofit
                    .Builder()
                    .baseUrl("https://routes.googleapis.com/")
                    .client(client)
                    .addConverterFactory(
                        Constants.Helpers.json
                            .asConverterFactory(
                                contentType = "application/json; charset=utf-8".toMediaType()
                            )
                    )
                    .build()
            }
    }

}