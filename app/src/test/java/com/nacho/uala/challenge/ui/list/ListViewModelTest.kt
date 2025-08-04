package com.nacho.uala.challenge.ui.list

import androidx.paging.PagingData
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

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
    fun `when GetCitiesUseCase emits data, ViewModel provides CityUiState in flow`() = runTest {
        val city = mockk<City>(relaxed = true)

        every { getCitiesUseCase.invoke() } returns flowOf(PagingData.from(listOf(city)))
        toggleCityFavoriteUseCase = mockk(relaxed = true)

        viewModel = ListViewModel(getCitiesUseCase, toggleCityFavoriteUseCase)

        val result = viewModel.cities.first()

        assertNotNull(result)
    }

    @Test
    fun `onToggleCityFavorite should call ToggleCityFavoriteUseCase`() = runTest {
        val city = mockk<City>(relaxed = true)
        val cityUiState = mockk<CityUiState>(relaxed = true)
        every { getCitiesUseCase.invoke() } returns flowOf(PagingData.from(listOf(city)))
        coEvery { toggleCityFavoriteUseCase.invoke(any()) } returns Result.Success(Unit)

        viewModel = ListViewModel(getCitiesUseCase, toggleCityFavoriteUseCase)

        viewModel.onToggleCityFavorite(cityUiState)

        advanceUntilIdle()

        coVerify { toggleCityFavoriteUseCase.invoke(any()) }
    }

    @Test
    fun `onCitySelected updates selectedCity`() = runTest {
        val city = mockk<City>(relaxed = true)
        every { getCitiesUseCase.invoke() } returns flowOf(PagingData.from(listOf(city)))

        viewModel = ListViewModel(getCitiesUseCase, toggleCityFavoriteUseCase)

        viewModel.onCitySelected(city)

        val selected = viewModel.selectedCity.value
        assertEquals(city, selected)
    }
}