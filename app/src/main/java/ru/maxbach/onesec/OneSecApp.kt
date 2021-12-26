package ru.maxbach.onesec

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.maxbach.onesec.di.appModule

class OneSecApp : Application() {

  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
      androidContext(this@OneSecApp)
      modules(appModule)
    }
  }
}
