package kaa.alisherbu.listbook.chapter.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.chapter.domain.usecase.GetChaptersByBookIdUseCase
import kaa.alisherbu.listbook.chapter.store.ChapterExecutor

@Module(includes = [ChapterModuleBinds::class])
class ChapterModule {

    @Provides
    internal fun provideExecutor(getChaptersUseCase: GetChaptersByBookIdUseCase): ChapterExecutor {
        return ChapterExecutor(getChaptersUseCase)
    }
}
