package kz.kolesateam.confapp

import androidx.multidex.MultiDexApplication
import kz.kolesateam.confapp.branchEvents.di.branchEventsModule
import kz.kolesateam.confapp.di.applicationModule
import kz.kolesateam.confapp.favorites.di.favoritesModule
import kz.kolesateam.confapp.upcomingEvents.di.upcomingEventsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConfApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConfApplication)
            modules(
                applicationModule,
                branchEventsModule,
                upcomingEventsModule,
                favoritesModule
            )
        }
    }
}