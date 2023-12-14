package kaa.alisherbu.listbook.domain.usecase

import kaa.alisherbu.listbook.domain.repository.AudioBooksRepository
import javax.inject.Inject

class RefreshUseCase @Inject constructor(
    private val audioBooksRepository: AudioBooksRepository
) {
    operator fun invoke() {
        audioBooksRepository.syncWithRemote()
    }
}
