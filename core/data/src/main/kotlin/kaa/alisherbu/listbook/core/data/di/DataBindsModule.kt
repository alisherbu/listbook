package kaa.alisherbu.listbook.core.data.di

import androidx.media3.exoplayer.offline.Download
import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.core.data.repository.AudioBooksRepositoryImpl
import kaa.alisherbu.listbook.core.data.repository.AudioDownloadsRepositoryImpl
import kaa.alisherbu.listbook.domain.repository.AudioBooksRepository
import kaa.alisherbu.listbook.domain.repository.AudioDownloadsRepository
import javax.inject.Singleton

@Module
interface DataBindsModule {

    @Binds
    fun audioBooksRepository(impl: AudioBooksRepositoryImpl): AudioBooksRepository

    @Binds
    @Singleton
    fun audioDownloadsRepository(impl: AudioDownloadsRepositoryImpl): AudioDownloadsRepository<Download?>
}