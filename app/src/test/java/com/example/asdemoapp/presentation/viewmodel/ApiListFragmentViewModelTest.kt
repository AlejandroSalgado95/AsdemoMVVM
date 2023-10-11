package com.example.asdemoapp.presentation.viewmodel

import org.junit.Assert.*

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.asdemoapp.MainCoroutineRule
import com.example.asdemoapp.data.repository.FakePublicApisRepositoryImpl
import com.example.asdemoapp.getOrAwaitValueTest
import com.example.asdemoapp.utils.ErrorType
import com.example.asdemoapp.utils.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ApiListFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ApiListFragmentViewModel
    private lateinit var repository: FakePublicApisRepositoryImpl

    @Before
    fun setup() {
        repository = FakePublicApisRepositoryImpl()
        viewModel = ApiListFragmentViewModel(repository)
    }

    @Test
    fun `call to the api when there is a server error, returns Error class`() {

        repository.setErrorType(ErrorType.ServerError())
        viewModel.getApiCategories()

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(ApiListFragmentViewModel.UiState.IsError::class.java)
    }

    @Test
    fun `call to the api when there is a network error, returns Error class`() {

        repository.setErrorType(ErrorType.NetworkError())
        viewModel.getApiCategories()

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(ApiListFragmentViewModel.UiState.IsError::class.java)
    }

    @Test
    fun `call to the api when there is an unknown error, returns Error class`() {

        repository.setErrorType(ErrorType.UnknownError())
        viewModel.getApiCategories()

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(ApiListFragmentViewModel.UiState.IsError::class.java)
    }

    @Test
    fun `call to the api when there is no error, returns Data class`() {

        viewModel.getApiCategories()

        val loadingState = viewModel.uiState.getOrAwaitValueTest()
        val resultState = viewModel.uiState.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(ApiListFragmentViewModel.UiState.Data::class.java)
    }
}
