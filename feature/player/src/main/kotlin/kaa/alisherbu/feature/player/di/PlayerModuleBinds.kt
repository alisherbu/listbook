package kaa.alisherbu.feature.player.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.feature.player.component.PlayerComponent
import kaa.alisherbu.feature.player.component.PlayerComponentImpl
import kaa.alisherbu.feature.player.store.PlayerStore
import kaa.alisherbu.feature.player.store.PlayerStoreImpl
import kaa.alisherbu.listbook.chapter.di.ChapterModule

@Module(includes = [ChapterModule::class])
internal interface PlayerModuleBinds {

    @Binds
    fun componentFactory(impl: PlayerComponentImpl.Factory): PlayerComponent.Factory

    @Binds
    fun playerStore(impl: PlayerStoreImpl): PlayerStore
}
