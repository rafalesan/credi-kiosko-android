package com.rafalesan.credikiosko.data.auth.mappers

import com.rafalesan.credikiosko.core.auth.data.dto.AuthResponseDto
import com.rafalesan.credikiosko.core.auth.data.dto.LoginRequestDto
import com.rafalesan.credikiosko.core.auth.data.dto.SignupRequestDto
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession

fun LoginUseCase.Credentials.toLoginRequest(): LoginRequestDto {
    return LoginRequestDto(this.email!!,
                           this.password!!,
                           this.deviceName!!)
}

fun AuthResponseDto.toUserSession(): UserSession {
    return UserSession(user.id!!,
                       user.businessId!!,
                       user.name!!,
                       user.nickname!!,
                       user.email!!,
                       token)
}

fun SignupUseCase.SignupData.toSignupRequest(): SignupRequestDto {
    return SignupRequestDto(name,
                            nickname,
                            businessName,
                            email,
                            password,
                            passwordConfirmation,
                            deviceName)
}
