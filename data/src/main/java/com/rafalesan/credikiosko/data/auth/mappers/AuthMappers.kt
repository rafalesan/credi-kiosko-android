package com.rafalesan.credikiosko.data.auth.mappers

import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.data.auth.entity.remote.AuthResponse
import com.rafalesan.credikiosko.data.auth.entity.remote.LoginRequest
import com.rafalesan.credikiosko.data.auth.entity.remote.SignupRequest
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.auth.usecases.SignupUseCase

fun LoginUseCase.Credentials.toLoginRequest(): LoginRequest {
    return LoginRequest(this.email!!,
                        this.password!!,
                        this.deviceName!!)
}

fun AuthResponse.toUserSession(): UserSession {
    return UserSession(user.id!!,
                       user.businessId!!,
                       user.name!!,
                       user.nickname!!,
                       user.email!!,
                       token)
}

fun SignupUseCase.SignupData.toSignupRequest(): SignupRequest {
    return SignupRequest(name,
                         nickname,
                         businessName,
                         email,
                         password,
                         passwordConfirmation,
                         deviceName)
}
