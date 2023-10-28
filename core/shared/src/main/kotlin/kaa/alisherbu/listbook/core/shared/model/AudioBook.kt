package kaa.alisherbu.listbook.core.shared.model

data class AudioBook(
    val id: String,
    val name: String,
    val audioUrl: String,
    val headerImage: String,
    val isDownloaded: Boolean
)
