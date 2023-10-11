package com.example.asdemoapp.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.asdemoapp.MainCoroutineRule
import com.example.asdemoapp.data.repository.FakePublicApisRepositoryImpl
import com.example.asdemoapp.data.repository.FakeSuperHeroRepositoryImpl
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.example.asdemoapp.getOrAwaitValueTest
import com.example.asdemoapp.utils.ErrorType
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SuperHeroSearchFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: SuperHeroSearchFragmentViewModel
    private lateinit var repository: FakeSuperHeroRepositoryImpl

    @Before
    fun setup() {
        repository = FakeSuperHeroRepositoryImpl()
        viewModel = SuperHeroSearchFragmentViewModel(repository)
    }


    @Test
    fun `call to the api when there is a server error, returns Error class`() {

        repository.setErrorType(ErrorType.ServerError())
        viewModel.getSuperHero("1")

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(SuperHeroSearchFragmentViewModel.UiState.IsError::class.java)
    }

    @Test
    fun `call to the api when there is a network error, returns Error class`() {

        repository.setErrorType(ErrorType.NetworkError())
        viewModel.getSuperHero("1")

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(SuperHeroSearchFragmentViewModel.UiState.IsError::class.java)
    }

    @Test
    fun `call to the api when there is an unknown error, returns Error class`() {

        repository.setErrorType(ErrorType.UnknownError())
        viewModel.getSuperHero("1")

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(SuperHeroSearchFragmentViewModel.UiState.IsError::class.java)
    }

    @Test
    fun `call to the api when there is no error, returns Data class`() {

        viewModel.getSuperHero("1")

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(SuperHeroSearchFragmentViewModel.UiState.Data::class.java)
    }

    @Test
    fun `observe the super hero's table when a new super hero is inserted, returns list of SuperHeroModel`() {

        viewModel.getSuperHero("1")
        val superHeroCacheList = viewModel.superHeroCacheList.getOrAwaitValueTest()

        assertThat(superHeroCacheList).contains(SuperHeroModel(id = "1"))
    }

}
