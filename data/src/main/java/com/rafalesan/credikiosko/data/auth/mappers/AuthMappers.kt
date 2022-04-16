package com.rafalesan.credikiosko.data.auth.mappers

import com.rafalesan.credikiosko.data.auth.entity.remote.LoginRequest
import com.rafalesan.credikiosko.data.auth.entity.remote.LoginResponse
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase

fun LoginUseCase.Credentials.toLoginRequest(): LoginRequest {
    return LoginRequest(this.email!!,
                        this.password!!,
                        this.deviceName!!)
}

fun LoginResponse.toUserSession(): UserSession {
    return UserSession(user.id!!,
                       user.businessId!!,
                       user.name!!,
                       user.nickname!!,
                       user.email!!,
                       token)
}
