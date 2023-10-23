package kaa.alisherbu.feature.player.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.feature.player.store.PlayerExecutor

@Module(includes = [PlayerModuleBinds::class])
class PlayerModule {

    @Provides
    internal fun provideExecutor(): PlayerExecutor {
        return PlayerExecutor()
    }
}