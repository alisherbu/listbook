package kaa.alisherbu.listbook.domain.repository

interface AudioDownloadsRepository<T> {

    fun isDownloaded(id: String): Boolean

    fun getDownload(id: String): T

    fun getDownloads(): List<T>
}