package com.rafalesan.credikiosko.data.auth.datasource.remote

import com.rafalesan.credikiosko.data.auth.entity.remote.LoginRequest
import com.rafalesan.credikiosko.data.auth.entity.remote.LoginResponse
import com.rafalesan.credikiosko.data.auth.utils.ApiHandler
import com.rafalesan.credikiosko.data.auth.utils.ApiResult
import com.rafalesan.credikiosko.data.base.BaseResponse

class AuthDataSource(private val authApi: IAuthApi) {

    suspend fun login(request: LoginRequest): ApiResult<BaseResponse<LoginResponse>> {
        return ApiHandler.performApiCall { authApi.login(request) }
    }

}
