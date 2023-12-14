package kaa.alisherbu.listbook.feature.home.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.domain.usecase.GetAudioBooksUseCase
import kaa.alisherbu.listbook.domain.usecase.RefreshUseCase
import kaa.alisherbu.listbook.feature.home.store.HomeExecutor

@Module(includes = [HomeModuleBinds::class])
class HomeModule {

    @Provides
    internal fun provideExecutor(
        getAudioBooksUseCase: GetAudioBooksUseCase,
        refreshUseCase: RefreshUseCase,
    ): HomeExecutor {
        return HomeExecutor(getAudioBooksUseCase, refreshUseCase)
    }
}
