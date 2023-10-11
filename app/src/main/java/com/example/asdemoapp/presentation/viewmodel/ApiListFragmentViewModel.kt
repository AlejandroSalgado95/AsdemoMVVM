package com.example.asdemoapp.presentation.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.ViewModel
import com.example.asdemoapp.utils.Resource
import com.example.asdemoapp.domain.model.ApiCategoryModel
import com.example.asdemoapp.domain.repository.PublicApisRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ApiListFragmentViewModel @Inject constructor(
    private val repository: PublicApisRepository
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    private val _isError: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    private val _dataList: MutableLiveData<List<ApiCategoryModel>> by lazy { MutableLiveData() }

    val uiState: LiveData<UiState> by lazy {
        MediatorLiveData<UiState>().apply {
            addSource(_isLoading) {
                value = UiState.IsLoading()
            }
            addSource(_isError) {
                value = UiState.IsError()
            }
            addSource(_dataList) {
                value = UiState.Data(it)
            }
        }
    }

    fun getApiCategories() {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = repository.getApiCategories()
            when (result) {
                is Resource.Success -> {
                    _dataList.postValue(result.data)
                }
                is Resource.Error -> {
                    _isError.postValue(true)
                }
            }
        }
    }

    sealed class UiState {
        class IsLoading : UiState()
        class IsError : UiState()
        class Data(val data: List<ApiCategoryModel>?) : UiState()
    }
}