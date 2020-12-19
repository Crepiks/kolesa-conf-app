package kz.kolesateam.confapp.branchEvents.di

import kz.kolesateam.confapp.branchEvents.data.BranchEventsDataSource
import kz.kolesateam.confapp.branchEvents.data.BranchEventsRepositoryImp
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.branchEvents.presentation.BranchEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val branchEventsModule: Module = module {

    viewModel {
        BranchEventsViewModel(
            branchEventsRepository = get(),
            favoritesRepository = get(),
            eventsNotificationAlarm = get()
        )
    }

    single<BranchEventsDataSource> {
        val apiClient = get<Retrofit>()
        apiClient.create(BranchEventsDataSource::class.java)
    }

    factory<BranchEventsRepository> {
        BranchEventsRepositoryImp(
            branchEventsDataSource = get(),
            favoritesRepository = get()
        )
    }
}