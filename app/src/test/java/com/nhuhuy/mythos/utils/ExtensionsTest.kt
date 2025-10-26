package com.nhuhuy.mythos.utils

import com.nhuhuy.mythos.core.utils.capitalizeName
import com.nhuhuy.mythos.core.utils.filterCategory
import com.nhuhuy.mythos.core.utils.filterName
import com.nhuhuy.mythos.creatures.domain.model.Creature
import com.nhuhuy.mythos.domain.FakeData.Companion.fakeCreatures
import org.junit.Assert
import org.junit.Test

class ExtensionsTest {

    private val fakeString = "nhu huy"

    @Test
    fun `capitalize string THEN return capitalized string`(){
        val result = fakeString.capitalizeName()
        Assert.assertEquals(result, "Nhu Huy")
    }

    @Test
    fun `filter creatures by category THEN return filtered creatures`() {
        val result = fakeCreatures.filterCategory("Huy")
        val assertedCreatures = listOf(Creature(
            author = "Huy",
            canon = "Huy",
            category = "Huy",
            id = 1,
            img = listOf("Huy"),
            name = "Huy",
            nicks = listOf("Huy"),
            overview = "overview",
            wikiUrl = "url"
        ))
        Assert.assertEquals(assertedCreatures,result)
    }

    @Test
    fun `filter creatures by name THEN return filtered creatures`() {
        val result = fakeCreatures.filterName("Huy")
        val assertedCreatures = listOf(Creature(
            author = "Huy",
            canon = "Huy",
            category = "Huy",
            id = 1,
            img = listOf("Huy"),
            name = "Huy",
            nicks = listOf("Huy"),
            overview = "overview",
            wikiUrl = "url"
        ))
        Assert.assertEquals(assertedCreatures, result )
    }
}