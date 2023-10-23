package kaa.alisherbu.feature.player.store

data class PlayerState(
    val title: String = "",
    val position: Long = 0L,
    val positionText: String = "",
    val duration: Long = 0L,
    val durationText: String = "",
    val userPosition: Long = 0L,
    val userPositionText: String = "",
    val isPlaying: Boolean = true
)