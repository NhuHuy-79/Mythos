package com.nhuhuy.mythos.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.data.local.room.CreatureDao
import com.nhuhuy.mythos.creatures.data.local.room.CreatureDatabase
import com.nhuhuy.mythos.creatures.data.mapper.toEntity
import com.nhuhuy.mythos.creatures.data.mapper.toModel
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.data.FakeData.Companion.fakeCreatures
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@SmallTest
class CreatureDatabaseTest {
    private lateinit var dao: CreatureDao
    private lateinit var db: CreatureDatabase


    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CreatureDatabase::class.java).build()
        dao = db.creatureDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    fun `insert all fetched creatures WHEN get all creatures THEN  return creature`() = runTest {
        val entities = fakeCreatures.map {
            it.toEntity()
        }
        dao.insertAll(entities)

        val expected = fakeCreatures.sortedBy { it.id }
        val actual = dao.getAll().map {
            it.toModel()
        }.sortedBy { it.id }

        Truth.assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `get creatures with id THEN return creature`() = runTest {
        val entities = fakeCreatures.map {
            it.toEntity()
        }
        dao.insertAll(entities)
        val expected = Creature(
            author = "Huy",
            canon = "Huy",
            category = "Huy",
            id = 1,
            img = listOf("Huy"),
            name = "Huy",
            nicks = listOf("Huy"),
            overview = "overview",
            wikiUrl = "url"
        )
        val actual = dao.getCreatureById(1).toModel()
        Truth.assertThat(actual).isEqualTo(expected)
    }

}