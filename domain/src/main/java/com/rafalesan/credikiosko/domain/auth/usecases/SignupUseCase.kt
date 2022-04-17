package com.rafalesan.credikiosko.domain.auth.usecases

import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.validators.SignupValidator
import com.rafalesan.credikiosko.domain.utils.Result

class SignupUseCase(private val authRepository: IAuthRepository) {

    suspend operator fun invoke(signupData: SignupData): Result<UserSession, SignupValidator.SignupValidation> {
        return Result.Failure.ApiFailure("No Implemented")
    }

    data class SignupData(val name: String?,
                          val nickname: String?,
                          val businessName: String?,
                          val email: String?,
                          val password: String?,
                          val passwordConfirmation: String?,
                          val deviceName: String?)

}
