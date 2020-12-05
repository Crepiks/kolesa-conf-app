package kz.kolesateam.confapp.branchEvents.di

import kz.kolesateam.confapp.branchEvents.data.BranchEventsDataSource
import kz.kolesateam.confapp.branchEvents.data.BranchEventsRepositoryImp
import kz.kolesateam.confapp.branchEvents.domain.BranchEventsRepository
import kz.kolesateam.confapp.branchEvents.presentation.BranchEventsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

const val API_BASE_URL = "http://37.143.8.68:2020/"

val branchEventsModule: Module = module {

    viewModel {
        BranchEventsViewModel(
            branchEventsRepository = get()
        )
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create()).build()
    }

    single<BranchEventsDataSource> {
        val apiClient = get<Retrofit>()
        apiClient.create(BranchEventsDataSource::class.java)
    }

    factory<BranchEventsRepository> {
        val branchEventsDataSource: BranchEventsDataSource = get()
        BranchEventsRepositoryImp(branchEventsDataSource)
    }
}