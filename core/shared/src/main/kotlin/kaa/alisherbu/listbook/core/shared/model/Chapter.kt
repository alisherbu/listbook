package kaa.alisherbu.listbook.core.shared.model

data class Chapter(
    val bookId: String,
    val audioUrl: String,
    val name: String,
    val isDownloaded: Boolean
)