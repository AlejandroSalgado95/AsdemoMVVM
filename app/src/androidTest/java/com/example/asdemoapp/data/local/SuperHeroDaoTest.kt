package com.example.asdemoapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.asdemoapp.data.remote.entity.SuperHeroEntity
import com.example.asdemoapp.getOrAwaitValueAndroidTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class SuperHeroDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: SuperHeroDatabase
    private lateinit var dao: SuperHeroDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.getSuperHeroDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertSuperHeroEntity() = runBlockingTest {

        val superHeroEntity = SuperHeroEntity(id = "1")
        dao.saveSuperHeroToDb(superHeroEntity)
        val allSuperHeroEntities = dao.observeAllSuperHeroesFromDb().getOrAwaitValueAndroidTest()

        assertThat(allSuperHeroEntities).contains(superHeroEntity)
    }
}













