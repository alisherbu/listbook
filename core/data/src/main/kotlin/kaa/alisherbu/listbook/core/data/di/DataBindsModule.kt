package kaa.alisherbu.listbook.core.data.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.core.data.repository.AudioBooksRepositoryImpl
import kaa.alisherbu.listbook.domain.repository.AudioBooksRepository

@Module
interface DataBindsModule {

    @Binds
    fun audioBooksRepository(impl: AudioBooksRepositoryImpl): AudioBooksRepository

}