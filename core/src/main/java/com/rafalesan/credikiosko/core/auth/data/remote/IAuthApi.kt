package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.core.auth.data.dto.AuthResponseDto
import com.rafalesan.credikiosko.core.auth.data.dto.LoginRequestDto
import com.rafalesan.credikiosko.core.auth.data.dto.SignupRequestDto
import com.rafalesan.credikiosko.core.commons.data.base.BaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface IAuthApi {

    @POST("businesses/login")
    suspend fun login(@Body request: LoginRequestDto): Response<BaseResponse<AuthResponseDto>>

    @POST("businesses/register")
    suspend fun signup(@Body request: SignupRequestDto): Response<BaseResponse<AuthResponseDto>>

}
