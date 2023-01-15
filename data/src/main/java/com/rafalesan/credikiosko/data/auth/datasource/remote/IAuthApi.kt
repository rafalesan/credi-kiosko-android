package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.core.commons.data.base.BaseResponse
import com.rafalesan.credikiosko.core.commons.data.dto.AuthResponse
import com.rafalesan.credikiosko.core.commons.data.dto.LoginRequest
import com.rafalesan.credikiosko.core.commons.data.dto.SignupRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {

    @POST("businesses/login")
    suspend fun login(@Body request: LoginRequest): Response<BaseResponse<AuthResponse>>

    @POST("businesses/register")
    suspend fun signup(@Body request: SignupRequest): Response<BaseResponse<AuthResponse>>

}
