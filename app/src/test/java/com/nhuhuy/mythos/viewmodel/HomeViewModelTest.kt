package com.nhuhuy.mythos.viewmodel

import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.usecase.FetchCreaturesUseCase
import com.nhuhuy.mythos.creatures.domain.usecase.ObserveCreaturesUseCase
import com.nhuhuy.mythos.creatures.presentation.home.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @Mock
    private lateinit var fetchCreaturesUseCase: FetchCreaturesUseCase

    @Mock
    private lateinit var observeCreatureUseCase: ObserveCreaturesUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())
        homeViewModel = HomeViewModel(observeCreatureUseCase, fetchCreaturesUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `update search query WHEN query is changed THEN update query state`() = runTest{
        val expected = "key"
        homeViewModel.updateSearchQuery("key")
        val actual = homeViewModel.state.value.query
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `retry to fetch data`(){
        val expected = Unit
        val actual = homeViewModel.onRetry()
        Truth.assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun `change status WHEN status is changed THEN update status state`(){
        val expected = true
        homeViewModel.changeSearchStatus(true)
        val actual = homeViewModel.state.value.isSearching

        Truth.assertThat(actual).isEqualTo(expected)
    }
}