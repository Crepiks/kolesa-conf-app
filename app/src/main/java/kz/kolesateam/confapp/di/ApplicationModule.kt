package kz.kolesateam.confapp.di

import kz.kolesateam.confapp.branchEvents.di.API_BASE_URL
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

val applicationModule: Module = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create()).build()
    }
}