package com.example.asdemoapp.presentation.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor() : ViewModel() {
    private val _photoUri: MutableLiveData<Uri> by lazy { MutableLiveData() }
    val photoUri get() = _photoUri

    fun setPhotoUri(uri: Uri){
        _photoUri.postValue(uri)
    }
}