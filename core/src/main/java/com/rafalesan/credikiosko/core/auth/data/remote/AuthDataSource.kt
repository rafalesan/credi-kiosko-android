package com.rafalesan.credikiosko.core.auth.data.remote

import com.rafalesan.credikiosko.core.auth.data.dto.AuthResponseDto
import com.rafalesan.credikiosko.core.auth.data.dto.LoginRequestDto
import com.rafalesan.credikiosko.core.auth.data.dto.SignupRequestDto
import com.rafalesan.credikiosko.core.commons.data.base.BaseResponse
import com.rafalesan.credikiosko.core.commons.data.utils.ApiResult
import com.rafalesan.credikiosko.core.commons.data.utils.IApiHandler

class AuthDataSource(private val authApi: IAuthApi,
                     private val apiHandler: IApiHandler) {

    suspend fun login(request: LoginRequestDto): ApiResult<BaseResponse<AuthResponseDto>> {
        return apiHandler.performApiCall { authApi.login(request) }
    }

    suspend fun signup(request: SignupRequestDto): ApiResult<BaseResponse<AuthResponseDto>> {
        return apiHandler.performApiCall { authApi.signup(request) }
    }

}
