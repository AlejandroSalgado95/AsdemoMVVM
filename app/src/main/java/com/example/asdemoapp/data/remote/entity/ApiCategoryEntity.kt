package com.example.asdemoapp.data.remote.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "api_category"
)

data class ApiCategoryEntity(
    @PrimaryKey
    @NonNull
    @SerializedName("API") val api: String,
    @SerializedName("Description") val description: String? = null,
    @SerializedName("Auth") val auth: String? = null,
    @SerializedName("HTTPS") val https: Boolean = false,
    @SerializedName("Cors") val cors: String? = null,
    @SerializedName("Link") val link: String? = null,
    @SerializedName("Category") val category: String? = null
)
