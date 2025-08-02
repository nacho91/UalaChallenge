package com.nacho.uala.challenge.domain.usecase

import com.nacho.uala.challenge.domain.repository.CityRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import com.nacho.uala.challenge.domain.util.Result
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class InitializeCitiesUseCaseTest {

    private lateinit var repository: CityRepository
    private lateinit var useCase: InitializeCitiesUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
        useCase = InitializeCitiesUseCase(repository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @Test
    fun `when local is empty and fetch succeeds, should return success`() = runTest {
        coEvery { repository.isLocalEmpty() } returns Result.Success(true)
        coEvery { repository.fetchCities() } returns Result.Success(emptyList())
        coEvery { repository.saveCities(any()) } returns Result.Success(Unit)

        val result = useCase()

        assertTrue(result is Result.Success)
    }

    @Test
    fun `when local is not empty, should return success without calling fetch`() = runTest {
        coEvery { repository.isLocalEmpty() } returns Result.Success(false)
        val result = useCase()

        assertTrue(result is Result.Success)
    }

    @Test
    fun `when fetch fails, should return error`() = runTest {
        coEvery { repository.isLocalEmpty() } returns Result.Success(true)
        coEvery { repository.fetchCities() } returns Result.Error.Network(Throwable("Network error"))

        val result = useCase()

        assertTrue(result is Result.Error.Network)
    }

    @Test
    fun `when save fails, should return error`() = runTest {
        coEvery { repository.isLocalEmpty() } returns Result.Success(true)
        coEvery { repository.fetchCities() } returns Result.Success(emptyList())
        coEvery { repository.saveCities(any()) } returns Result.Error.Database(Throwable("Database error"))

        val result = useCase()

        assertTrue(result is Result.Error.Database)
    }
}