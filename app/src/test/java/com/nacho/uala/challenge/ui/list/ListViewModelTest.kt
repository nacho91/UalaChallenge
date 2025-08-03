package com.nacho.uala.challenge.ui.list

import app.cash.turbine.test
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.domain.usecase.GetCitiesUseCase
import com.nacho.uala.challenge.domain.usecase.ToggleCityFavoriteUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.nacho.uala.challenge.domain.util.Result
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private lateinit var getCitiesUseCase: GetCitiesUseCase
    private lateinit var toggleCityFavoriteUseCase: ToggleCityFavoriteUseCase

    private lateinit var viewModel: ListViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getCitiesUseCase = mockk()
        toggleCityFavoriteUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when GetCitiesUseCase returns success, ViewModel updates uiState with cities`() = runTest {
        val city = mockk<City>(relaxed = true)
        every { getCitiesUseCase.invoke() } returns flowOf(Result.Success(listOf(city)))

        viewModel = ListViewModel(getCitiesUseCase, toggleCityFavoriteUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val endState = awaitItem()
            assertEquals(false, endState.isLoading)
            assertEquals(false, endState.isError)
            assertNotNull(endState.cities)
        }
    }

    @Test
    fun `when GetCitiesUseCase returns error, ViewModel sets isError`() = runTest {
        every { getCitiesUseCase.invoke() } returns
                flowOf(Result.Error.Database(Throwable("Database error")))

        viewModel = ListViewModel(getCitiesUseCase, toggleCityFavoriteUseCase)

        viewModel.uiState.test {
            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val endState = awaitItem()
            assertEquals(false, endState.isLoading)
            assertEquals(true, endState.isError)
            assertNull(endState.cities)
        }
    }

    @Test
    fun `onToggleCityFavorite should call ToggleCityFavoriteUseCase`() = runTest {
        val city = mockk<City>(relaxed = true)
        every { getCitiesUseCase.invoke() } returns flowOf(Result.Success(listOf(city)))
        coEvery { toggleCityFavoriteUseCase.invoke(any()) } returns Result.Success(Unit)

        viewModel = ListViewModel(getCitiesUseCase, toggleCityFavoriteUseCase)

        viewModel.onToggleCityFavorite(city)

        advanceUntilIdle()

        coVerify { toggleCityFavoriteUseCase.invoke(any()) }
    }
}