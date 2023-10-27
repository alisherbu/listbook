package kaa.alisherbu.listbook.feature.home.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.feature.home.domain.usecase.LoadAudioBooksUseCase
import kaa.alisherbu.listbook.feature.home.store.HomeExecutor
import kaa.alisherbu.service.player.AudioPlayer

@Module(includes = [HomeModuleBinds::class])
class HomeModule {

    @Provides
    internal fun provideExecutor(
        loadAudioBooksUseCase: LoadAudioBooksUseCase,
        audioPlayer: AudioPlayer,
    ): HomeExecutor {
        return HomeExecutor(loadAudioBooksUseCase, audioPlayer)
    }
}
