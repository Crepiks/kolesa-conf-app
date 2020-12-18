package kz.kolesateam.confapp.favorites.di

import kz.kolesateam.confapp.favorites.data.DefaultFavoritesRepositoryImpl
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val favoritesModule: Module = module {

    single<FavoritesRepository> {
        DefaultFavoritesRepositoryImpl()
    }
}