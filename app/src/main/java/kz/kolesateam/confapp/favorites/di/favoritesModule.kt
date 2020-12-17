package kz.kolesateam.confapp.favorites.di

import kz.kolesateam.confapp.favorites.data.DefaultFavoritesRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val favoritesModule: Module = module {

    single {
        DefaultFavoritesRepositoryImpl()
    }
}