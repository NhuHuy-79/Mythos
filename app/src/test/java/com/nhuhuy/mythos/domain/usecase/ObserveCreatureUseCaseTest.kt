package com.nhuhuy.mythos.domain.usecase

import com.nhuhuy.mythos.creatures.data.repository.CreatureRepositoryImp
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.usecase.ObserveCreaturesUseCase
import com.nhuhuy.mythos.domain.FakeData.Companion.fakeCreatures
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ObserveCreatureUseCaseTest {
    @InjectMocks
    private lateinit var useCaseTest: ObserveCreaturesUseCase

    @Mock
    private lateinit var repositoryImp: CreatureRepositoryImp

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `observe creatures WHEN fetch creatures from network THEN Resource Success`() = runTest {
        val expected = Resource.Success(fakeCreatures)
        val exception = Resource.Failure(Exception())
        given(repositoryImp.getCreatures()).willReturn(exception)
        given(repositoryImp.fetchCreatures()).willReturn(expected)

        val actual = useCaseTest().toList()
        val expectedResources = listOf(Resource.Loading, expected)

        Assert.assertEquals(expectedResources, actual)
        then(repositoryImp).should().getCreatures()
        then(repositoryImp).should().fetchCreatures()
    }

    @Test
    fun `observe creatures WHEN get creatures from local THEN Resource Success`() = runTest {
        val expected = Resource.Success(fakeCreatures)
        given(repositoryImp.getCreatures()).willReturn(expected)
        val actual = useCaseTest().toList()
        val expectedResources = listOf(Resource.Loading, expected)
        Assert.assertEquals(expectedResources, actual)
        then(repositoryImp).should().getCreatures()
    }
}