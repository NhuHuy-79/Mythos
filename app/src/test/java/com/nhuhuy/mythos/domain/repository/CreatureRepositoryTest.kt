package com.nhuhuy.mythos.domain.repository

import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.data.network.CreatureDTO
import com.nhuhuy.mythos.creatures.data.repository.CreatureRepositoryImp
import com.nhuhuy.mythos.creatures.data.source.CreatureLocalDataSource
import com.nhuhuy.mythos.creatures.data.source.CreatureNetworkDataSource
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import com.nhuhuy.mythos.data.FakeData.Companion.fakeCreature
import com.nhuhuy.mythos.data.FakeData.Companion.fakeCreatures
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CreatureRepositoryTest {
    @Mock
    private lateinit var fakeLocalDataSource: CreatureLocalDataSource

    @Mock
    private lateinit var fakeNetworkDataSource: CreatureNetworkDataSource

    private lateinit var fakeRepository : CreatureRepositoryImp

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        fakeRepository = CreatureRepositoryImp(fakeLocalDataSource, fakeNetworkDataSource)
    }

    @Test
    fun `fetch creatures THEN return list of creatures`() = runTest {
        val expectedData = listOf<CreatureDTO>()
        given(fakeNetworkDataSource.fetchCreatureList()).willReturn(expectedData)

        val actual = fakeRepository.fetchCreatures()
        Truth.assertThat(actual).isEqualTo(Resource.Success(expectedData.map {
            it.toModel()
        }))
    }

    //Test getCreatureById
    @Test
    fun `get creatures by id THEN return creature`() = runTest {
        val expected = fakeCreature
        given(fakeLocalDataSource.getCreatureById(1)).willReturn(fakeCreature.toEntity())

        val actual = fakeRepository.getCreaturesById(1)

        Truth.assertThat(actual).isEqualTo(Resource.Success(expected))
    }

    //Test  getCreatures
    @Test
    fun `get all creatures THEN return list of creatures`() = runTest {
        val expectedEntities = fakeCreatures.map {
            it.toEntity()
        }
        val expectedList = expectedEntities.map { it.toModel() }
        given(fakeLocalDataSource.getAll()).willReturn(expectedEntities)

        val actual = fakeRepository.getCreatures()

        Truth.assertThat(actual).isEqualTo(Resource.Success(expectedList))
    }

    //Test save Creatures
    @Test
    fun `save all creatures THEN return list of creatures`() = runTest {
        val expected = Unit
        val entities = listOf<Creature>()
        given(fakeLocalDataSource.insertAll(entities)).willReturn(Unit)

        val actual = fakeRepository.saveCreatures(entities)

        Truth.assertThat(actual).isEqualTo(expected)
    }
}