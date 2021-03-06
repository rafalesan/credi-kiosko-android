package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.data.auth.entity.remote.AuthResponse
import com.rafalesan.credikiosko.data.auth.entity.remote.LoginRequest
import com.rafalesan.credikiosko.data.auth.entity.remote.SignupRequest
import com.rafalesan.credikiosko.data.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {

    @POST("businesses/login")
    suspend fun login(@Body request: LoginRequest): Response<BaseResponse<AuthResponse>>

    @POST("businesses/register")
    suspend fun signup(@Body request: SignupRequest): Response<BaseResponse<AuthResponse>>

}
