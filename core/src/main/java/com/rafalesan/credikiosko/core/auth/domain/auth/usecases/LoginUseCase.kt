package com.rafalesan.credikiosko.core.auth.domain.auth.usecases

import com.rafalesan.credikiosko.core.auth.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.core.auth.domain.auth.validators.CredentialsValidator
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.usecases.SaveUserSessionUseCase
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: IAuthRepository,
    private val saveUserSessionUseCase: SaveUserSessionUseCase
) {

    suspend operator fun invoke(credentials: Credentials): ResultOf<UserSession, CredentialsValidator.CredentialValidation> {

        val validations = listOfNotNull(
            CredentialsValidator.validateEmail(credentials.email),
            CredentialsValidator.validatePassword(credentials.password),
            CredentialsValidator.validateDeviceName(credentials.deviceName))

        if (validations.isNotEmpty()) {
            return ResultOf.Failure.InvalidData(validations)
        }

        val result = authRepository.login(credentials)

        if(result is ResultOf.Success) {
            saveUserSessionUseCase.invoke(result.value)
        }

        return result

    }

    data class Credentials(val email: String?,
                           val password: String?,
                           val deviceName: String?)

}
