package com.rafalesan.credikiosko.data.auth.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.common.truth.Truth.assertWithMessage
import com.rafalesan.credikiosko.data.FileResourceHelper.getBufferedSourceOf
import com.rafalesan.credikiosko.data.auth.datasource.local.UserSessionDataSource
import com.rafalesan.credikiosko.data.auth.datasource.remote.AuthDataSource
import com.rafalesan.credikiosko.data.auth.datasource.remote.IAuthApi
import com.rafalesan.credikiosko.data.utils.ApiHandler
import com.rafalesan.credikiosko.data.utils.ConnectivityHelper
import com.rafalesan.credikiosko.domain.auth.entity.UserSession
import com.rafalesan.credikiosko.domain.auth.repository.IAuthRepository
import com.rafalesan.credikiosko.domain.auth.usecases.LoginUseCase
import com.rafalesan.credikiosko.domain.utils.ResultOf
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
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
        authRepository = AuthRepository(authDataSource,
                                        userSessionDataSource)
    }

    @Test
    fun `when login success then get result of success with user session`() {

        val source = getBufferedSourceOf("login_success_response.json")
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        mockServer.enqueue(mockResponse)

        runTest {

            val result = authRepository.login(LoginUseCase.Credentials("", "", ""))
            if (result !is ResultOf.Success) {
                fail("result is not ResultOf.Success")
                return@runTest
            }

            assertWithMessage("result.value is not an UserSession")
                    .that(result.value)
                    .isInstanceOf(UserSession::class.java)

        }
    }

}
