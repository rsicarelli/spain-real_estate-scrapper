package me.rsicarelli.domain.entity

import domain.entity.Model

data class Ratings(
    override val _id: String,
    val userId: String,
    val upVotedProperties: List<String>,
    val downVotedProperties: List<String>
) : Model
