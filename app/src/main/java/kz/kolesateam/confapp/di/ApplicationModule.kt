package kz.kolesateam.confapp.di

import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

const val API_BASE_URL = "http://37.143.8.68:2020"

val applicationModule: Module = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create()).build()
    }
}