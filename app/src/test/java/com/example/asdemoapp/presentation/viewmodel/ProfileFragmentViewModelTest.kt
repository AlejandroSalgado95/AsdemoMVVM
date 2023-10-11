package com.example.asdemoapp.presentation.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.asdemoapp.getOrAwaitValueTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class ProfileFragmentViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProfileFragmentViewModel

    @Before
    fun setup() {
        viewModel = ProfileFragmentViewModel()
    }

    @Test
    fun `set an uri, returns the uri`() {

        viewModel.setPhotoUri(Uri.parse("1"))

        val resultState = viewModel.photoUri.getOrAwaitValueTest()

        assertThat(resultState).isInstanceOf(Uri::class.java)
    }
}