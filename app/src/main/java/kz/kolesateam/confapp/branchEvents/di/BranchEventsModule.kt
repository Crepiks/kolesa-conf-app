package kz.kolesateam.confapp.branchEvents.di

import kz.kolesateam.confapp.branchEvents.data.BranchEventsApiClient
import kz.kolesateam.confapp.common.API_BASE_URL
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val branchEventsModule: Module = module {
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