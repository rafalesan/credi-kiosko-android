package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.data.auth.entity.remote.AuthResponse
import com.rafalesan.credikiosko.data.auth.entity.remote.LoginRequest
import com.rafalesan.credikiosko.data.auth.entity.remote.SignupRequest
import com.rafalesan.credikiosko.data.base.BaseResponse
import com.rafalesan.credikiosko.data.utils.ApiHandler
import com.rafalesan.credikiosko.data.utils.ApiResult

class AuthDataSource(private val authApi: IAuthApi) {

    suspend fun login(request: LoginRequest): ApiResult<BaseResponse<AuthResponse>> {
        return ApiHandler.performApiCall { authApi.login(request) }
    }

    suspend fun signup(request: SignupRequest): ApiResult<BaseResponse<AuthResponse>> {
        return ApiHandler.performApiCall { authApi.signup(request) }
    }

}
