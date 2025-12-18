package com.github.inlinefun.lazygo.data

import retrofit2.http.Body
import retrofit2.http.POST

interface RoutingAPI {

    @POST(
        value = "directions/v2:computeRoutes"
    )
    suspend fun getRoute(
        @Body request: RouteRequest
    ): RouteResponse

}