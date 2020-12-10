package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.branchEvents.di.branchEventsModule
import kz.kolesateam.confapp.di.applicationModule
import org.koin.core.context.startKoin

class ConfApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                applicationModule,
                branchEventsModule
            )
        }
    }
}