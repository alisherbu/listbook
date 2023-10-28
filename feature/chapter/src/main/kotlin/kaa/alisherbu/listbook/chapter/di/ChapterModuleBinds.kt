package kaa.alisherbu.listbook.chapter.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.chapter.component.ChapterComponent
import kaa.alisherbu.listbook.chapter.component.ChapterComponentImpl

@Module
internal interface ChapterModuleBinds {

    @Binds
    fun componentFactory(impl: ChapterComponentImpl.Factory): ChapterComponent.Factory
}