package kz.kolesateam.confapp.di

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kz.kolesateam.confapp.notifications.EventsNotificationAlarm
import kz.kolesateam.confapp.notifications.EventsNotificationManager
import org.koin.android.ext.koin.androidApplication
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

    single {
        ObjectMapper().registerKotlinModule()
    }

    single {
        EventsNotificationManager(
            context = androidApplication()
        )
    }

    factory {
        EventsNotificationAlarm(
            application = androidApplication()
        )
    }
}