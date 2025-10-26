package com.nhuhuy.mythos.data.source

import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.data.local.room.CreatureDao
import com.nhuhuy.mythos.creatures.data.local.room.CreatureEntity
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.data.FakeData.Companion.fakeCreature
import com.nhuhuy.mythos.data.FakeData.Companion.fakeCreatures
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given
import kotlin.math.exp

class LocalDataSourceTest {

    @Mock
    private lateinit var dao: CreatureDao

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `save all creatures THEN return Unit`() = runTest {
        val expected = Unit
        val creatures: List<Creature> = fakeCreatures
        val entities = creatures.map { it.toEntity() }
        given(dao.insertAll(entities)).willReturn(Unit)

        val actual = dao.insertAll(entities)
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get all creatures THEN return list of creatures`() = runTest {
        val expected: List<CreatureEntity> = fakeCreatures.map { it.toEntity() }
        given(dao.getAll()).willReturn(expected)
        val actual = dao.getAll()
        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get creatures by id THEN return creature`() = runTest {
        val expected: CreatureEntity = fakeCreature.toEntity()
        given(dao.getCreatureById(1)).willReturn(expected)

        val actual = dao.getCreatureById(1)
        Truth.assertThat(actual).isEqualTo(expected)
    }

}