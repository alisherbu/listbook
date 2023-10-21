package kaa.alisherbu.listbook.feature.home.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kaa.alisherbu.listbook.feature.home.data.model.AudioBookResponse
import kaa.alisherbu.listbook.feature.home.domain.repository.AudioBooksRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

internal class AudioBookRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : AudioBooksRepository {

    override fun subscribeToAudioBooks(): Flow<List<AudioBookResponse>> {
        return callbackFlow {
            val subscription = firebaseFirestore.collection("audio-books")
                .addSnapshotListener { snapshot, _ ->
                    snapshot?.documents?.mapNotNull {
                        AudioBookResponse(
                            id = it["id"].toString(),
                            name = it["name"].toString()
                        )
                    }?.also(::trySend)
                }
            awaitClose(subscription::remove)
        }
    }
}