package com.nacho.uala.challenge.ui.detail

import app.cash.turbine.test
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.usecase.GetCityByIdUseCase
import com.nacho.uala.challenge.domain.util.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CityDetailViewModelTest {

    private lateinit var getCityByIdUseCase: GetCityByIdUseCase

    private lateinit var viewModel: CityDetailViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getCityByIdUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadCity updates state with city on success`() = runTest {
        val city = mockk<City>(relaxed = true)
        coEvery { getCityByIdUseCase(1) } returns Result.Success(city)

        viewModel = CityDetailViewModel(getCityByIdUseCase)

        viewModel.loadCity(1)

        advanceUntilIdle()

        viewModel.uiState.test {
            val item = awaitItem()

            assertNotNull(item.city)
            assertEquals(false, item.isError)
        }
    }

    @Test
    fun `loadCity sets isError to true on failure`() = runTest {
        coEvery { getCityByIdUseCase(1) } returns Result.Error.Database(Throwable("Database error"))

        viewModel = CityDetailViewModel(getCityByIdUseCase)

        viewModel.loadCity(1)

        advanceUntilIdle()

        viewModel.uiState.test {
            val item = awaitItem()

            assertNull(item.city)
            assertEquals(true, item.isError)
        }
    }
}