package com.nhuhuy.mythos.domain.usecase

import com.nhuhuy.mythos.creatures.data.repository.CreatureRepositoryImp
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.repository.CreatureRepository
import com.nhuhuy.mythos.creatures.domain.usecase.FetchCreaturesUseCase
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.mockito.kotlin.wheneverBlocking

class FetchCreaturesUseCaseTest {

    @InjectMocks
    private lateinit var useCaseTest: FetchCreaturesUseCase

    @Mock
    private lateinit var repositoryImp: CreatureRepository

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `fetch creatures give a resource THEN Resource Success`() = runTest {
        val expected = Resource.Success<List<Creature>>(emptyList())

        given(repositoryImp.fetchCreatures()).willReturn(expected)

        val actual = useCaseTest()

        then(repositoryImp).should().fetchCreatures()
        Assert.assertEquals(expected, actual)
    }
}