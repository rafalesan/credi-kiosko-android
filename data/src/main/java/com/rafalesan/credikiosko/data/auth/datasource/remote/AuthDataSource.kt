package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.core.commons.data.base.BaseResponse
import com.rafalesan.credikiosko.core.commons.data.entity.remote.AuthResponse
import com.rafalesan.credikiosko.core.commons.data.entity.remote.LoginRequest
import com.rafalesan.credikiosko.core.commons.data.entity.remote.SignupRequest
import com.rafalesan.credikiosko.core.commons.data.utils.ApiResult
import com.rafalesan.credikiosko.core.commons.data.utils.IApiHandler

class AuthDataSource(private val authApi: IAuthApi,
                     private val apiHandler: IApiHandler) {

    suspend fun login(request: LoginRequest): ApiResult<BaseResponse<AuthResponse>> {
        return apiHandler.performApiCall { authApi.login(request) }
    }

    suspend fun signup(request: SignupRequest): ApiResult<BaseResponse<AuthResponse>> {
        return apiHandler.performApiCall { authApi.signup(request) }
    }

}
