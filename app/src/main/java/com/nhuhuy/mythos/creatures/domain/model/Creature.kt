package com.nhuhuy.mythos.creatures.domain.model


data class Creature(
    val author: String,
    val canon: String,
    val category: String,
    val id: Int,
    val img: List<String>,
    val name: String,
    val nicks: List<String>,
    val overview: String,
    val wikiUrl: String
)


