package kaa.alisherbu.listbook.core.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kaa.alisherbu.listbook.core.database.dao.AudioBooksDao
import kaa.alisherbu.listbook.core.database.entity.AudioBookEntity
import kaa.alisherbu.listbook.core.shared.coroutine.AppDispatchers
import kaa.alisherbu.listbook.domain.model.AudioBookResponse
import kaa.alisherbu.listbook.domain.repository.AudioBooksRepository
import kaa.alisherbu.service.player.AudioDownloadManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AudioBooksRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val audioBooksDao: AudioBooksDao,
    private val dispatchers: AppDispatchers,
    private val downloadManager: AudioDownloadManager
) : AudioBooksRepository {
    private val ioScope = CoroutineScope(dispatchers.io)

    init {
        syncWithRemote()
        downloadManager.addListener(object : AudioDownloadManager.Listener {
            override fun onDownloadCompleted(id: String) {
                ioScope.launch {
                    val entity = audioBooksDao.getAudioBook(id)
                    audioBooksDao.update(entity.copy(isDownloaded = true))
                }
            }

            override fun onDownloadRemoved(id: String) {
                ioScope.launch {
                    val entity = audioBooksDao.getAudioBook(id)
                    audioBooksDao.update(entity.copy(isDownloaded = false))
                }
            }

        })
    }

    override fun getAudioBooksFlow(): Flow<List<AudioBookResponse>> {
        return audioBooksDao.getAudioBooks().map(::toAudioBookList).flowOn(dispatchers.io)
    }

    override fun syncWithRemote() {
        firebaseFirestore.collection("audio-books").get()
            .addOnSuccessListener {
                val audioBooks = it.documents.map(::toAudioBookEntity)
                ioScope.launch { audioBooksDao.insertAll(audioBooks) }
            }
    }

    private fun toAudioBookList(books: List<AudioBookEntity>): List<AudioBookResponse> {
        return books.map(::toAudioBookResponse)
    }

    private fun toAudioBookResponse(entity: AudioBookEntity): AudioBookResponse {
        return AudioBookResponse(
            id = entity.id,
            name = entity.name,
            audioUrl = entity.audioUrl,
            isDownloaded = entity.isDownloaded
        )
    }

    private fun toAudioBookEntity(snapshot: DocumentSnapshot): AudioBookEntity {
        return AudioBookEntity(
            id = snapshot.id,
            name = snapshot["name"] as? String,
            audioUrl = snapshot["audioUrl"] as? String,
            isDownloaded = downloadManager.isDownloaded(snapshot.id)
        )
    }
}