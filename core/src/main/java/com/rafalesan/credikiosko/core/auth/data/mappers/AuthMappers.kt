package com.rafalesan.credikiosko.data.auth.mappers

import com.rafalesan.credikiosko.core.auth.data.dto.AuthResponse
import com.rafalesan.credikiosko.core.auth.data.dto.LoginRequest
import com.rafalesan.credikiosko.core.auth.data.dto.SignupRequest
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession

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
