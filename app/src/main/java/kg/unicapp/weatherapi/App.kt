package kg.unicapp.weatherapi

import android.app.Application
import kg.unicapp.weatherapi.di.dataModule
import kg.unicapp.weatherapi.di.vmModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(vmModule, dataModule))
        }
    }
}