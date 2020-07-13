package com.cindy.cindypodcasttest.Model

data class CastModel(
    var castData: CastData? = null
)

data class CastData(
    var podcast: List<Podcast>? = null
)

data class Podcast(
    var artistName: String? = null,
    var artworkUrl100: String? = null,
    var id: String? = null,
    var name: String? = null
)