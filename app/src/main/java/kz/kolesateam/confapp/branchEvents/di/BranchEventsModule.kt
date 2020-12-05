package kz.kolesateam.confapp.branchEvents.di

import kz.kolesateam.confapp.branchEvents.data.BranchEventsApiClient
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
            branchEventsApiClient = get()
        )
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create()).build()
    }

    single<BranchEventsApiClient> {
        val apiClient = get<Retrofit>()
        apiClient.create(BranchEventsApiClient::class.java)
    }
}