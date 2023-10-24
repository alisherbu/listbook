package kaa.alisherbu.listbook.feature.main.store

import kaa.alisherbu.listbook.core.shared.model.AudioBook

data class MainState(
    val isPlaying: Boolean = false,
    val currentAudioBook: AudioBook? = null
)