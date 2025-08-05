package com.nacho.uala.challenge.domain.repository

import com.nacho.uala.challenge.data.local.CityLocalDataSource
import com.nacho.uala.challenge.data.local.model.CityEntity
import com.nacho.uala.challenge.data.remote.CityRemoteDataSource
import com.nacho.uala.challenge.data.remote.model.CityDTO
import com.nacho.uala.challenge.data.remote.model.CoordinatesDTO
import com.nacho.uala.challenge.domain.model.City
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import com.nacho.uala.challenge.domain.util.Result
import io.mockk.Runs
import io.mockk.just
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class CityRepositoryImplTest {

    private lateinit var remote: CityRemoteDataSource
    private lateinit var local: CityLocalDataSource
    private lateinit var repository: CityRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        remote = mockk()
        local = mockk()

        repository = CityRepositoryImpl(remote, local)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchCities returns Success when remote returns data`() = runTest {
        val dto = CityDTO(1, "Test", "AR", CoordinatesDTO(0.0, 0.0))
        coEvery { remote.fetchCities() } returns listOf(dto)

        val result = repository.fetchCities()

        assertTrue(result is Result.Success)
        assertEquals("Test", (result as Result.Success).data.first().country)
    }

    @Test
    fun `fetchCities returns Network error on HttpException`() = runTest {
        coEvery{ remote.fetchCities() } throws HttpException(
            Response.error<Any>(500, "error".toResponseBody())
        )

        val result = repository.fetchCities()

        assertTrue(result is Result.Error.Network)
    }

    @Test
    fun `getCityById returns Success when city is found`() = runTest {
        val entity = CityEntity(1, "Test", "AR", 0.0, 0.0, false)
        coEvery { local.getCityById(1) } returns entity

        val result = repository.getCityById(1)

        assertTrue(result is Result.Success)
        assertEquals("Test", (result as Result.Success).data.name)
    }

    @Test
    fun `getCityById returns Database error when city not found`() = runTest {
        coEvery { local.getCityById(1) } returns null

        val result = repository.getCityById(1)

        assertTrue(result is Result.Error.Database)
    }

    @Test
    fun `saveCities returns Success`() = runTest {
        coEvery { local.saveAll(any()) } just Runs

        val result = repository.saveCities(emptyList())

        assertTrue(result is Result.Success)
    }

    @Test
    fun `toggleCityFavorite calls local update with inverted favorite`() = runTest {
        val city = City(1, "Test", "AR", 0.0, 0.0, false)
        coEvery { local.update(match { it.id == 1 && it.isFavorite }) } just Runs

        val result = repository.toggleCityFavorite(city)

        assertTrue(result is Result.Success)
    }

    @Test
    fun `isLocalEmpty returns Success with true`() = runTest {
        coEvery { local.isEmpty() } returns true

        val result = repository.isLocalEmpty()

        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data)
    }
}