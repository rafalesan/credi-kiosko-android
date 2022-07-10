package com.rafalesan.credikiosko.data

import com.rafalesan.credikiosko.data.utils.ApiHandler
import com.rafalesan.credikiosko.data.utils.ApiResult
import com.rafalesan.credikiosko.data.utils.IApiHandler
import com.rafalesan.credikiosko.data.utils.IConnectivityHelper
import com.rafalesan.credikiosko.data.utils.exceptions.ApiException
import com.rafalesan.credikiosko.data.utils.exceptions.NoInternetException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ApiHandlerTest {

    companion object {
        private const val API_SUCCESS_BODY = "Response is ok"
        private const val API_ERROR_MESSAGE = "An error occurred in server."
        private const val ERROR_RESPONSE = "{ \"message\": \"$API_ERROR_MESSAGE\" }"
    }

    private lateinit var apiHandler: IApiHandler

    private fun getMockConnectivityWith(internetConnectivityResult: Boolean): IConnectivityHelper {
        val connectivityHelper = mockk<IConnectivityHelper>()
        coEvery { connectivityHelper.isInternetAvailable() } returns internetConnectivityResult
        return connectivityHelper
    }

    private fun buildErrorResponseBody(): ResponseBody {
        return ResponseBody.create(MediaType.parse("application/json"),
                                   ERROR_RESPONSE)
    }

    @Test
    fun `when no internet available then get api result error with no internet exception`() = runTest {

        val connectivityHelper = getMockConnectivityWith(false)
        apiHandler = ApiHandler(connectivityHelper)

        //dummy apiCall just to test no internet validation
        val apiCall: suspend () -> Response<String> = {
            Response.success(API_SUCCESS_BODY)
        }

        val apiResult = apiHandler.performApiCall { apiCall() }

        if(apiResult !is ApiResult.Error) {
            Assert.fail("apiResult should be an ApiResult.Error")
            return@runTest
        }

        assert(apiResult.exception is NoInternetException) {
            "apiResult.exception should be a NoInternetException"
        }
    }

    @Test
    fun `when response is not success then get api result error with api exception`() = runTest {
        val connectivityHelper = getMockConnectivityWith(true)
        apiHandler = ApiHandler(connectivityHelper)

        //dummy apiCall just to test returning an ApiException
        val apiCall: suspend () -> Response<String> = {
            Response.error(422, buildErrorResponseBody())
        }

        val apiResult = apiHandler.performApiCall { apiCall() }

        if(apiResult !is ApiResult.Error) {
            Assert.fail("apiResult should be an ApiResult.Error")
            return@runTest
        }

        assert(apiResult.exception is ApiException)  {
            "apiResult.exception should be an ApiException"
        }

        assert(apiResult.exception.message == API_ERROR_MESSAGE) {
            "apiResult.exception.message is not equals to API_ERROR_MESSAGE"
        }
    }

    @Test
    fun `when response is success then get api result success with value`() = runTest {
        val connectivityHelper = getMockConnectivityWith(true)
        apiHandler = ApiHandler(connectivityHelper)

        val apiCall: suspend () -> Response<String> = {
            Response.success(API_SUCCESS_BODY)
        }

        val apiResult = apiHandler.performApiCall { apiCall() }

        if(apiResult !is ApiResult.Success) {
            Assert.fail("apiResult should be an ApiResult.Success")
            return@runTest
        }

        assert(apiResult.response == API_SUCCESS_BODY)

    }

}
