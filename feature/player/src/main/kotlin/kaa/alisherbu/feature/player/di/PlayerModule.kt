package kaa.alisherbu.feature.player.di

import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import kaa.alisherbu.feature.player.store.PlayerExecutor

@Module(includes = [PlayerModuleBinds::class])
class PlayerModule {

    @Provides
    internal fun provideExecutor(exoPlayer: ExoPlayer): PlayerExecutor {
        return PlayerExecutor(exoPlayer)
    }
}