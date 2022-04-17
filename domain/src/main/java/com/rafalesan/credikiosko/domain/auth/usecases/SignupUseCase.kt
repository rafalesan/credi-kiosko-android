package com.rafalesan.credikiosko.domain.auth.usecases

import com.rafalesan.credikiosko.domain.account.usecases.SaveUserSessionUseCase
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.validators.SignupValidator
import com.rafalesan.credikiosko.domain.utils.Result

class SignupUseCase(private val authRepository: IAuthRepository,
                    private val saveUserSessionUseCase: SaveUserSessionUseCase) {

    suspend operator fun invoke(signupData: SignupData): Result<UserSession, SignupValidator.SignupValidation> {

        val validations = listOfNotNull(
            SignupValidator.validateName(signupData.name),
            SignupValidator.validateBusinessName(signupData.nickname),
            SignupValidator.validateNickname(signupData.businessName),
            SignupValidator.validateEmail(signupData.email),
            SignupValidator.validatePassword(signupData.password, signupData.passwordConfirmation),
            SignupValidator.validatePasswordConfirmation(signupData.passwordConfirmation),
            SignupValidator.validateDeviceName(signupData.deviceName))

        if(validations.isNotEmpty()) {
            return Result.InvalidData(validations)
        }

        val result = authRepository.signup(signupData)

        if(result is Result.Success) {
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
