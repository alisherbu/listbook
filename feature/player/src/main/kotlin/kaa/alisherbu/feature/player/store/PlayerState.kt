package kaa.alisherbu.feature.player.store

import kaa.alisherbu.listbook.core.shared.model.AudioBook

data class PlayerState(
    val currentAudioBook: AudioBook? = null,
    val position: Long = 0L,
    val positionText: String = "",
    val duration: Long = 0L,
    val durationText: String = "",
    val userPosition: Long = 0L,
    val userPositionText: String = "",
    val isPlaying: Boolean = true,
)
