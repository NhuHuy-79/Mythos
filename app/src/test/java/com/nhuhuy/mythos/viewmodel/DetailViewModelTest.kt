package com.nhuhuy.mythos.viewmodel

import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.usecase.GetCreatureUseCase
import com.nhuhuy.mythos.creatures.presentation.detail.DetailViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @Mock
    private lateinit var getCreatureUseCase: GetCreatureUseCase

    private lateinit var detailViewModel: DetailViewModel


    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(StandardTestDispatcher())
        detailViewModel = DetailViewModel(getCreatureUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `update image state THEN return success`(){
        val expected = "img"
        detailViewModel.updateImageState("img")
        val actual = detailViewModel.state.value.imgUrl
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get creature by id THEN return success`() = runTest{
        val excepted = Resource.Success(
            Creature(
                author = "Author",
                canon = "Author",
                category = "Outer god",
                id = 1,
                img = listOf("img"),
                name = "Name",
                nicks = emptyList(),
                overview = "Nothing",
                wikiUrl = "url"
            )
        )
        given(getCreatureUseCase(id = 1)).willReturn(excepted)
        detailViewModel.getCreatureDetailById(1)
        advanceUntilIdle()
        val actual = detailViewModel.state.value.resource
        Truth.assertThat(actual).isEqualTo(excepted)

    }
}