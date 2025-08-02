package com.nacho.uala.challenge.ui.splash

import app.cash.turbine.test
import com.nacho.uala.challenge.domain.usecase.InitializeCitiesUseCase
import com.nacho.uala.challenge.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SplashViewModelTest {

    private lateinit var useCase: InitializeCitiesUseCase
    private lateinit var viewModel: SplashViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        useCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `should emit success state when initialization succeeds`() = runTest {
        coEvery { useCase() } returns Result.Success(Unit)

        viewModel = SplashViewModel(useCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            assertEquals(false, loadingState.isSuccess)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(true, finalState.isSuccess)
            assertEquals(false, finalState.isError)
        }
    }

    @Test
    fun `should emit error state when initialization fails`() = runTest {
        coEvery { useCase() } returns Result.Error.Unknown(Throwable("Unknown error"))

        viewModel = SplashViewModel(useCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)
            assertEquals(false, loadingState.isSuccess)

            val finalState = awaitItem()
            assertEquals(false, finalState.isLoading)
            assertEquals(false, finalState.isSuccess)
            assertEquals(true, finalState.isError)
        }
    }
}