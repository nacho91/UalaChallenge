package com.nacho.uala.challenge.ui.list

import android.content.res.Configuration
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.nacho.uala.challenge.domain.model.City
import com.nacho.uala.challenge.utils.WithOrientation
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class ListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listScreen_displaysCityInPortrait() {
        val mockViewModel = mockk<ListViewModel>(relaxed = true)

        every { mockViewModel.uiState } returns MutableStateFlow(
            ListUiState(
                cities = emptyList(),
                isLoading = false
            )
        )

        every { mockViewModel.selectedCity } returns MutableStateFlow(null)

        composeTestRule.setContent {
            WithOrientation(Configuration.ORIENTATION_PORTRAIT) {
                ListScreen(
                    viewModel = mockViewModel,
                    navigateMap = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("city_list_container")
            .assertExists()

        composeTestRule
            .onNodeWithTag("city_map_container")
            .assertDoesNotExist()
    }

    @Test
    fun listScreen_displaysCityInLandscape() {
        val mockViewModel = mockk<ListViewModel>(relaxed = true)

        every { mockViewModel.uiState } returns MutableStateFlow(
            ListUiState(
                cities = emptyList(),
                isLoading = false
            )
        )

        every { mockViewModel.selectedCity } returns MutableStateFlow(null)

        composeTestRule.setContent {
            WithOrientation(Configuration.ORIENTATION_LANDSCAPE) {
                ListScreen(
                    viewModel = mockViewModel,
                    navigateMap = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("city_list_container")
            .assertExists()

        composeTestRule
            .onNodeWithTag("city_map_container")
            .assertExists()
    }

    @Test
    fun listScreen_callsViewModel_onFavoriteClick() {
        val mockViewModel = mockk<ListViewModel>(relaxed = true)

        val testCity = City(
            id = 1,
            name = "Buenos Aires",
            country = "AR",
            lat = -34.6,
            lon = -58.4,
            isFavorite = false
        )

        every { mockViewModel.uiState } returns MutableStateFlow(
            ListUiState(
                isLoading = false,
                cities = listOf(testCity)
            )
        )

        every { mockViewModel.selectedCity } returns MutableStateFlow(null)

        composeTestRule.setContent {
            WithOrientation(Configuration.ORIENTATION_PORTRAIT) {
                ListScreen(
                    viewModel = mockViewModel,
                    navigateMap = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("favorite_button")
            .performClick()

        verify { mockViewModel.onToggleCityFavorite(testCity) }
    }

    @Test
    fun cityClick_callsOnCitySelected() {
        val mockViewModel = mockk<ListViewModel>(relaxed = true)

        val testCity = City(
            id = 1,
            name = "Buenos Aires",
            country = "AR",
            lat = -34.6,
            lon = -58.4,
            isFavorite = false
        )

        every { mockViewModel.uiState } returns MutableStateFlow(
            ListUiState(
                cities = listOf(testCity),
                isLoading = false,
                isError = false
            )
        )
        every { mockViewModel.selectedCity } returns MutableStateFlow(null)

        composeTestRule.setContent {
            ListScreen(
                viewModel = mockViewModel,
                navigateMap = {}
            )
        }

        composeTestRule
            .onNodeWithTag("city_item")
            .performClick()

        verify { mockViewModel.onCitySelected(testCity) }
    }
}