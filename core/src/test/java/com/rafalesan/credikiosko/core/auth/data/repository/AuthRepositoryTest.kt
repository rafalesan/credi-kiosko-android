package com.rafalesan.credikiosko.core.auth.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.common.truth.Truth.assertWithMessage
import com.rafalesan.credikiosko.core.auth.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.core.auth.domain.auth.usecases.SignupUseCase
import com.rafalesan.credikiosko.core.commons.data.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.core.commons.data.utils.ApiHandler
import com.rafalesan.credikiosko.core.commons.data.utils.ConnectivityHelper
import com.rafalesan.credikiosko.core.commons.domain.entity.UserSession
import com.rafalesan.credikiosko.core.commons.domain.utils.ResultOf
import com.rafalesan.credikiosko.core.testextensions.enqueue
import com.rafalesan.credikiosko.data.auth.datasource.remote.AuthDataSource
import com.rafalesan.credikiosko.data.auth.datasource.remote.IAuthApi
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@ExperimentalCoroutinesApi
class AuthRepositoryTest {

    private lateinit var authRepository: IAuthRepository
    private lateinit var authDataSource: AuthDataSource
    private lateinit var authApi: IAuthApi
    private lateinit var userSessionDataSource: UserSessionDataSource
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var mockServer: MockWebServer

    @Before
    fun setup() {
        setupMockServer()
        setupAuthApi()
        setupAuthDataSource()
        setupDataStore()
        setupUserSessionDataSource()
        setupAuthRepository()
    }

    private fun setupMockServer() {
        mockServer = MockWebServer()
    }

    private fun setupAuthApi() {
        authApi = Retrofit.Builder()
                .baseUrl(mockServer.url(""))
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(IAuthApi::class.java)
    }

    private fun setupAuthDataSource() {
        authDataSource = AuthDataSource(authApi, ApiHandler(ConnectivityHelper()))
    }

    private fun setupDataStore() {
        val context = mockk<Context>(relaxed = true)
        dataStore = preferencesDataStore(name = "userSessionPreferences").getValue(context, String::javaClass)
    }

    private fun setupUserSessionDataSource() {
        userSessionDataSource = UserSessionDataSource(dataStore)
    }

    private fun setupAuthRepository() {
        authRepository = AuthRepository(authDataSource)
    }

    @Test
    fun `when login success then get result of success with user session`() {

        mockServer.enqueue("auth/login_success_response.json")

        runTest {

            val result = authRepository.login(LoginUseCase.Credentials("rafalesan96@gmail.com",
                                                                       "password",
                                                                       "S21 de Rafael"))
            if (result !is ResultOf.Success) {
                fail("result is not ResultOf.Success")
                return@runTest
            }

            assertWithMessage("result.value is not an UserSession")
                    .that(result.value)
                    .isInstanceOf(UserSession::class.java)

        }
    }

    @Test
    fun `when login unsuccessful then get result of failure api failure`() {

        mockServer.enqueue("auth/login_unsuccessful_response.json", 422)

        runTest {

            val result = authRepository.login(LoginUseCase.Credentials("rafalesan96@gmail.com",
                                                                       "password",
                                                                       "S21 de Rafael"))

            assertWithMessage("result is not an ResultOf.Failure.ApiFailure")
                    .that(result)
                    .isInstanceOf(ResultOf.Failure.ApiFailure::class.java)

        }

    }

    @Test
    fun `when signup success then get result of success`() {

        mockServer.enqueue("auth/signup_success_response.json")

        runTest {

            val result = authRepository.signup(SignupUseCase.SignupData("Rafael Alegría Sánchez",
                                                                        "rafalesan",
                                                                        "rafalesan corporation",
                                                                        "rafalesan96@gmail.com",
                                                                        "password",
                                                                        "password",
                                                                        "S21 de Rafael"))
            if (result !is ResultOf.Success) {
                fail("result is not ResultOf.Success")
                return@runTest
            }

            assertWithMessage("result.value is not an UserSession")
                    .that(result.value)
                    .isInstanceOf(UserSession::class.java)

        }

    }

    @Test
    fun `when signup unsuccessful then get result of failure api failure`() {

        mockServer.enqueue("auth/signup_unsuccessful_response.json", 422)

        runTest {

            val result = authRepository.signup(SignupUseCase.SignupData("Rafael Alegría Sánchez",
                                                                        "rafalesan",
                                                                        "rafalesan corporation",
                                                                        "rafalesan96@gmail.com",
                                                                        "password",
                                                                        "password",
                                                                        "S21 de Rafael"))

            assertWithMessage("result is not an ResultOf.Failure.ApiFailure")
                    .that(result)
                    .isInstanceOf(ResultOf.Failure.ApiFailure::class.java)

        }

    }

}
