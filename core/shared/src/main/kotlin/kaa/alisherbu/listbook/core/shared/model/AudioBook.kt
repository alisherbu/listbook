package kaa.alisherbu.listbook.core.shared.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AudioBook(
    val id: String,
    val name: String,
    val audioUrl: String,
    val isDownloaded: Boolean
) : Parcelable
