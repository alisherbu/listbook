package kaa.alisherbu.listbook.feature.main.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.core.shared.player.AudioPlayer
import kaa.alisherbu.listbook.feature.main.store.MainExecutor

@Module(includes = [MainModuleBinds::class])
class MainModule {

    @Provides
    internal fun provideExecutor(audioPlayer: AudioPlayer): MainExecutor {
        return MainExecutor(audioPlayer)
    }
}
