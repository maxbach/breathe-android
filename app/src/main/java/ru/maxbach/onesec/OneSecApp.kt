package ru.maxbach.onesec

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.maxbach.onesec.di.appModule

class OneSecApp : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger()
      androidContext(this@OneSecApp)
      modules(appModule)
    }
  }
}
