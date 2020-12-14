package kz.kolesateam.confapp.upcomingEvents.di

import kz.kolesateam.confapp.upcomingEvents.data.UpcomingEventsDataSource
import kz.kolesateam.confapp.upcomingEvents.data.UpcomingEventsRepositoryImp
import kz.kolesateam.confapp.upcomingEvents.domain.UpcomingEventsRepository
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val upcomingEventsModule: Module = module {

    single<UpcomingEventsDataSource> {
        val apiClient = get<Retrofit>()
        apiClient.create(UpcomingEventsDataSource::class.java)
    }

    factory<UpcomingEventsRepository> {
        UpcomingEventsRepositoryImp(
            upcomingEventsDataSource = get()
        )
    }
}