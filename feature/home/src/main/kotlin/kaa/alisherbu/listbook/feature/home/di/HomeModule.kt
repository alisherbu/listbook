package kaa.alisherbu.listbook.feature.home.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.domain.usecase.GetAudioBooksUseCase
import kaa.alisherbu.listbook.domain.usecase.GetChaptersUseCase
import kaa.alisherbu.listbook.feature.home.domain.usecase.RefreshUseCase
import kaa.alisherbu.listbook.feature.home.store.HomeExecutor
import kaa.alisherbu.service.player.AudioPlayer

@Module(includes = [HomeModuleBinds::class])
class HomeModule {

    @Provides
    internal fun provideExecutor(
        getAudioBooksUseCase: GetAudioBooksUseCase,
        refreshUseCase: RefreshUseCase,
        audioPlayer: AudioPlayer,
    ): HomeExecutor {
        return HomeExecutor(getAudioBooksUseCase, refreshUseCase, audioPlayer)
    }
}
