package kz.kolesateam.confapp

import android.app.Application
import kz.kolesateam.confapp.branchEvents.di.branchEventsModule
import org.koin.core.context.startKoin

class ConfApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                branchEventsModule
            )
        }
    }
}