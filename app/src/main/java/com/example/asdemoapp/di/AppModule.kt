package com.example.asdemoapp.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.asdemoapp.R
import com.example.asdemoapp.data.local.SuperHeroDao
import com.example.asdemoapp.data.local.SuperHeroDatabase
import com.example.asdemoapp.data.remote.PublicApisApi
import com.example.asdemoapp.data.remote.SuperHeroApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePublicApisApi(): PublicApisApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(PublicApisApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()
            .create(PublicApisApi::class.java)
    }

    @Singleton
    @Provides
    fun myInt(): Int{
        val someInt = 3
        return someInt
    }

    @Singleton
    @Provides
    fun provideSuperHeroApi(): SuperHeroApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClientBuilder = OkHttpClient.Builder()
        httpClientBuilder.addInterceptor(logging)
        return Retrofit.Builder()
            .baseUrl(SuperHeroApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClientBuilder.build())
            .build()
            .create(SuperHeroApi::class.java)
    }

    @Singleton
    @Provides
    @Named("production_db")
    fun provideSuperHeroDataBase(@ApplicationContext context: Context): SuperHeroDatabase {
        return Room.databaseBuilder(context, SuperHeroDatabase::class.java, "super_hero_db.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideSuperHeroDao(@Named("production_db") database: SuperHeroDatabase): SuperHeroDao {
        return database.getSuperHeroDao()
    }

    @Singleton
    @Provides
    fun provideGlide(@ApplicationContext context: Context): RequestManager{
        return Glide.with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
        )
    }

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context{
        return context
    }

}