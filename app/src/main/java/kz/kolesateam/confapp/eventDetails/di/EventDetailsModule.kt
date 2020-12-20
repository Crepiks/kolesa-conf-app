package kz.kolesateam.confapp.eventDetails.di

import kz.kolesateam.confapp.eventDetails.data.EventDetailsDataSource
import kz.kolesateam.confapp.eventDetails.data.EventDetailsRepositoryImpl
import kz.kolesateam.confapp.eventDetails.domain.EventDetailsRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val eventDetailsModule: Module = module {

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