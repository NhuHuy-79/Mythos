package com.nhuhuy.mythos.utils

import com.nhuhuy.mythos.core.utils.capitalizeName
import com.nhuhuy.mythos.core.utils.filterCategory
import com.nhuhuy.mythos.core.utils.filterName
import com.nhuhuy.mythos.creatures.domain.model.Creature
import org.junit.Assert
import org.junit.Test

class UtilsTest {

    private val fakeString = "nhu huy"
    private val fakeCreatures = listOf(
        Creature(
            author = "Huy",
            canon = "Huy",
            category = "Huy",
            id = 1,
            img = listOf("Huy"),
            name = "Huy",
            nicks = listOf("Huy"),
            overview = "overview",
            wikiUrl = "url"
        ),
        Creature(
            author = "Azathoth",
            canon = "Azathoth",
            category = "Azathoth",
            id = 2,
            img = listOf("Azathoth"),
            name = "Azathoth",
            nicks = listOf("Azathoth"),
            overview = "overview",
            wikiUrl = "url"
        ),
        Creature(
            author = "Cthulhu",
            canon = "Cthulhu",
            category = "Cthulhu",
            id = 3,
            img = listOf("Cthulhu"),
            name = "Cthulhu",
            nicks = listOf("Cthulhu"),
            overview = "overview",
            wikiUrl = "url"
        ),
    )

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