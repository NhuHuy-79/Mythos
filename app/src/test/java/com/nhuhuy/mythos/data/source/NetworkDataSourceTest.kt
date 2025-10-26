package com.nhuhuy.mythos.data.source

import com.google.common.truth.Truth
import com.nhuhuy.mythos.creatures.data.network.CreatureApi
import com.nhuhuy.mythos.creatures.data.network.CreatureDTO
import com.nhuhuy.mythos.domain.FakeData.Companion.fakeCreatures
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.given

class NetworkDataSourceTest {
    @Mock
    private lateinit var api: CreatureApi

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `fetch list of creatures THEN return list of creatures`() = runTest {
        val expected = listOf<CreatureDTO>()
        given(api.fetchCreatureList()).willReturn(expected)

        val actual = api.fetchCreatureList()
        Truth.assertThat(actual).isEqualTo(expected)
    }
}