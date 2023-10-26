package kaa.alisherbu.feature.player.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.feature.player.store.PlayerExecutor
import kaa.alisherbu.player.AudioPlayer

@Module(includes = [PlayerModuleBinds::class])
class PlayerModule {

    @Provides
    internal fun provideExecutor(audioPlayer: AudioPlayer): PlayerExecutor {
        return PlayerExecutor(audioPlayer)
    }
}
