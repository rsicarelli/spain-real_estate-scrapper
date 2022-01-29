package me.rsicarelli.domain.valueobject

data class UpVoteInput(val propertyId: String)
data class DownVoteInput(val propertyId: String)

data class RatingResponse(val upVoted: List<String>, val downVoted: List<String>)