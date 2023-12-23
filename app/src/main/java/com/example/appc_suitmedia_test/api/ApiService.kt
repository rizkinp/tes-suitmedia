package com.example.appc_suitmedia_test.api

import com.example.appc_suitmedia_test.model.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
//Api Service
interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int, @Query("per_page") perPage: Int): Response<UserResponse>
}
