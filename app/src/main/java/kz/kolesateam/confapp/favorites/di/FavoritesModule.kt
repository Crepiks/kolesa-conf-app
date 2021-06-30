package kz.kolesateam.confapp.favorites.di

import kz.kolesateam.confapp.favorites.data.DefaultFavoritesRepositoryImpl
import kz.kolesateam.confapp.favorites.domain.FavoritesRepository
import kz.kolesateam.confapp.favorites.presentation.FavoritesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val favoritesModule: Module = module {

    viewModel {
        FavoritesViewModel(
            favoritesRepository = get()
        )
    }

    single<FavoritesRepository> {
        DefaultFavoritesRepositoryImpl(
            context = androidApplication(),
            objectMapper = get()
        )
    }
}