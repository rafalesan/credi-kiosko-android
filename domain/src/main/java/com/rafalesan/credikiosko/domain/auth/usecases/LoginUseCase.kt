package com.rafalesan.credikiosko.domain.auth.usecases

import com.rafalesan.credikiosko.domain.account.usecases.SaveUserSessionUseCase
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.validators.CredentialsValidator
import com.rafalesan.credikiosko.domain.utils.Result

class LoginUseCase(private val authRepository: IAuthRepository,
                   private val saveUserSessionUseCase: SaveUserSessionUseCase) {

    suspend operator fun invoke(credentials: Credentials): Result<UserSession, CredentialsValidator.CredentialValidation> {

        val validations = listOfNotNull(
                CredentialsValidator.validateEmail(credentials.email),
                CredentialsValidator.validatePassword(credentials.password),
                CredentialsValidator.validateDeviceName(credentials.deviceName))

        if (validations.isNotEmpty()) {
            return Result.InvalidData(validations)
        }

        val result = authRepository.login(credentials)

        if(result is Result.Success) {
            saveUserSessionUseCase.invoke(result.value)
        }

        return result

    }

    data class Credentials(val email: String?,
                           val password: String?,
                           val deviceName: String?)

}
