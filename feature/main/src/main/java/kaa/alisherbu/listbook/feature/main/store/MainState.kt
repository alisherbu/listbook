package kaa.alisherbu.listbook.feature.main.store

import kaa.alisherbu.listbook.core.shared.model.AudioBook
import kaa.alisherbu.listbook.core.shared.model.Chapter

data class MainState(
    val isPlaying: Boolean = false,
    val currentChapter: Chapter? = null,
    val currentAudioBook: AudioBook? = null
)
