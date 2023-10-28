package kaa.alisherbu.listbook.chapter.di

import dagger.Binds
import dagger.Module
import kaa.alisherbu.listbook.chapter.component.ChapterComponent
import kaa.alisherbu.listbook.chapter.component.ChapterComponentImpl
import kaa.alisherbu.listbook.chapter.store.ChapterStore
import kaa.alisherbu.listbook.chapter.store.ChapterStoreImpl

@Module
internal interface ChapterModuleBinds {

    @Binds
    fun componentFactory(impl: ChapterComponentImpl.Factory): ChapterComponent.Factory

    @Binds
    fun chapterStore(impl: ChapterStoreImpl): ChapterStore
}