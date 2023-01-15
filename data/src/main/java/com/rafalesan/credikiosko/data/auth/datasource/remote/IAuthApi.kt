package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.core.commons.data.base.BaseResponse
import com.rafalesan.credikiosko.core.commons.data.entity.remote.AuthResponse
import com.rafalesan.credikiosko.core.commons.data.entity.remote.LoginRequest
import com.rafalesan.credikiosko.core.commons.data.entity.remote.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {

    @POST("businesses/login")
    suspend fun login(@Body request: LoginRequest): Response<BaseResponse<AuthResponse>>

    @POST("businesses/register")
    suspend fun signup(@Body request: SignupRequest): Response<BaseResponse<AuthResponse>>

}
