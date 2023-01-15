package com.rafalesan.credikiosko.domain.auth.usecases

import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.usecases.SaveUserSessionUseCase
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.validators.SignupValidator

class SignupUseCase(private val authRepository: IAuthRepository,
                    private val saveUserSessionUseCase: SaveUserSessionUseCase) {

    suspend operator fun invoke(signupData: SignupData): ResultOf<UserSession, SignupValidator.SignupValidation> {

        val validations = listOfNotNull(
            SignupValidator.validateName(signupData.name),
            SignupValidator.validateBusinessName(signupData.nickname),
            SignupValidator.validateNickname(signupData.businessName),
            SignupValidator.validateEmail(signupData.email),
            SignupValidator.validatePassword(signupData.password, signupData.passwordConfirmation),
            SignupValidator.validatePasswordConfirmation(signupData.passwordConfirmation),
            SignupValidator.validateDeviceName(signupData.deviceName))

        if(validations.isNotEmpty()) {
            return ResultOf.InvalidData(validations)
        }

        val result = authRepository.signup(signupData)

        if(result is ResultOf.Success) {
            saveUserSessionUseCase.invoke(result.value)
        }

        return result
    }

    data class SignupData(val name: String,
                          val nickname: String,
                          val businessName: String,
                          val email: String,
                          val password: String,
                          val passwordConfirmation: String,
                          val deviceName: String)

}
