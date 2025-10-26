package com.nhuhuy.mythos.domain.repository

import com.nhuhuy.mythos.creatures.data.repository.CreatureRepositoryImp
import com.nhuhuy.mythos.creatures.data.source.CreatureLocalDataSource
import com.nhuhuy.mythos.creatures.data.source.CreatureNetworkDataSource
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.creatures.domain.model.Resource
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

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
    fun `fetch creatures THEN return list of creatures`(){
        val expected = Resource.Success<List<Creature>>(emptyList())



    }
}