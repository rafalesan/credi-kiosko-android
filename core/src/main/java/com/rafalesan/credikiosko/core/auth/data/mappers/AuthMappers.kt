package com.rafalesan.credikiosko.core.auth.data.mappers

import com.rafalesan.credikiosko.core.auth.data.dto.AuthResponseDto
import com.rafalesan.credikiosko.core.auth.data.dto.BusinessDto
import com.rafalesan.credikiosko.core.auth.data.dto.LoginRequestDto
import com.rafalesan.credikiosko.core.auth.data.dto.SignupRequestDto
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.domain.entity.Business
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession

fun LoginUseCase.Credentials.toLoginRequest(): LoginRequestDto {
    return LoginRequestDto(this.email!!,
                           this.password!!,
                           this.deviceName!!)
}

fun AuthResponseDto.toUserSession(): UserSession {
    return UserSession(
        user.id!!,
        user.businessId!!,
        user.name!!,
        user.nickname!!,
        user.email!!,
        token,
        user.business.toBusiness()
    )
}

fun BusinessDto.toBusiness() = Business(
    id,
    name,
    email
)

fun SignupUseCase.SignupData.toSignupRequest(): SignupRequestDto {
    return SignupRequestDto(name,
                            nickname,
                            businessName,
                            email,
                            password,
                            passwordConfirmation,
                            deviceName)
}
