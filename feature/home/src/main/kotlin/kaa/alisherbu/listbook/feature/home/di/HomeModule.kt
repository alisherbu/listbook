package kaa.alisherbu.listbook.feature.home.di

import dagger.Module
import dagger.Provides
import kaa.alisherbu.listbook.feature.home.store.HomeExecutor

@Module(includes = [HomeModuleBinds::class])
class HomeModule {

    @Provides
    internal fun provideExecutor(): HomeExecutor {
        return HomeExecutor()
    }
}