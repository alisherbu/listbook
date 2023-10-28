package kaa.alisherbu.listbook.chapter.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.chapter.store.ChapterExecutor
import kaa.alisherbu.listbook.domain.usecase.GetChaptersUseCase

@Module(includes = [ChapterModuleBinds::class])
class ChapterModule {

    @Provides
    internal fun provideExecutor(getChaptersUseCase: GetChaptersUseCase): ChapterExecutor {
        return ChapterExecutor(getChaptersUseCase)
    }
}