package com.nhuhuy.mythos.data

import com.nhuhuy.mythos.creatures.domain.model.Creature

class FakeData {
    companion object {
        val fakeCreature =  Creature(
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

        val fakeCreatures = listOf(
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
    }
}
