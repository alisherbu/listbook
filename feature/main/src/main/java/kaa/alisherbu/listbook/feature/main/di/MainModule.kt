package kaa.alisherbu.listbook.feature.main.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.feature.main.domain.usecase.GetChaptersByBookIdFlowUseCase
import kaa.alisherbu.listbook.feature.main.store.MainExecutor
import kaa.alisherbu.service.player.AudioPlayer

@Module(includes = [MainModuleBinds::class])
class MainModule {

    @Provides
    internal fun provideExecutor(
        audioPlayer: AudioPlayer,
        getChaptersByBookIdUseCase: GetChaptersByBookIdFlowUseCase
    ): MainExecutor {
        return MainExecutor(
            audioPlayer,
            getChaptersByBookIdUseCase
        )
    }
}
