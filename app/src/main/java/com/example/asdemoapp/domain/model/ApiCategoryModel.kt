package com.example.asdemoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ApiCategoryModel (
    val api: String? = null,
    val description : String? = null,
    val auth: String? = null,
    val https: Boolean = false,
    val cors : String? = null,
    val link : String? = null,
    val category: String? = null
): Parcelable
