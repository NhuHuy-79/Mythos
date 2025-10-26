package com.nhuhuy.mythos.domain.usecase

import com.nhuhuy.mythos.creatures.data.repository.CreatureRepositoryImp
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.creatures.domain.repository.CreatureRepository
import com.nhuhuy.mythos.creatures.domain.usecase.GetCreatureUseCase
import com.nhuhuy.mythos.domain.FakeData.Companion.fakeCreature
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.mockito.kotlin.then

class GetCreatureUseCaseTest {

    @InjectMocks
    private lateinit var useCaseTest: GetCreatureUseCase
    @Mock
    private lateinit var repositoryImp: CreatureRepositoryImp


    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `get creatures  give a resource THEN Resource Success`() = runTest {
        val expected = Resource.Success(fakeCreature)

        given(repositoryImp.getCreaturesById(1)).willReturn(expected)

        val actual = useCaseTest(1)

        then(repositoryImp).should().getCreaturesById(1)
        Assert.assertEquals(expected, actual)
        then(repositoryImp).should().getCreatures()
        then(repositoryImp).should().fetchCreatures()
        then(repositoryImp).shouldHaveNoInteractions()
    }


}