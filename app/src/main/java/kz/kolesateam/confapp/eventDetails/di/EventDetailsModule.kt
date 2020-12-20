package kz.kolesateam.confapp.eventDetails.di

import kz.kolesateam.confapp.eventDetails.data.EventDetailsDataSource
import kz.kolesateam.confapp.eventDetails.data.EventDetailsRepositoryImpl
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import kz.kolesateam.confapp.eventDetails.presentation.EventDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val eventDetailsModule: Module = module {

    viewModel {
        EventDetailsViewModel(
            eventDetailsRepository = get(),
            favoritesRepository = get(),
            eventsNotificationAlarm = get()
        )
    }

    single<EventDetailsDataSource> {
        val apiClient = get<Retrofit>()
        apiClient.create(EventDetailsDataSource::class.java)
    }

    factory<EventDetailsRepository> {
        EventDetailsRepositoryImpl(
            eventDetailsDataSource = get(),
            favoritesRepository = get()
        )
    }
}