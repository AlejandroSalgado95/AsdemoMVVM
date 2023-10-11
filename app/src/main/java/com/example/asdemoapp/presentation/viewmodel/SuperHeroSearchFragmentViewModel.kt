package com.example.asdemoapp.presentation.viewmodel

import androidx.lifecycle.*
import com.example.asdemoapp.utils.Resource
import com.example.asdemoapp.domain.model.SuperHeroModel
import com.example.asdemoapp.domain.repository.SuperHeroRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SuperHeroSearchFragmentViewModel @Inject constructor(
    private val repository: SuperHeroRepository
) : ViewModel() {

    private val _isLoading: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    private val _isError: MutableLiveData<Boolean> by lazy { MutableLiveData() }
    private val _data: MutableLiveData<SuperHeroModel> by lazy { MutableLiveData() }
    val superHeroCacheList = repository.observeAllSuperHeroesFromDb()

    val uiState: LiveData<UiState> by lazy {
        MediatorLiveData<UiState>().apply {
            addSource(_isLoading) {
                value = UiState.IsLoading
            }
            addSource(_isError) {
                value = UiState.IsError
            }
            addSource(_data) {
                value = UiState.Data(it)
            }
        }
    }

    fun getSuperHero(superHeroId: String) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            val result = repository.getSuperHero(superHeroId)
            when (result) {
                is Resource.Success -> {
                    _data.postValue(result.data)
                }
                is Resource.Error -> {
                    _isError.postValue(true)
                }
            }
        }
    }

    sealed class UiState {
        object IsLoading : UiState()
        object IsError : UiState()
        data class Data(val data: SuperHeroModel?) : UiState()
    }
}