package ru.maxbach.onesec.presentation.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.inject
import ru.maxbach.onesec.BuildConfig
import ru.maxbach.onesec.domain.models.MobileApp
import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.usecase.GetForbiddenAppUseCase
import ru.maxbach.onesec.domain.usecase.IsAppSystemUseCase
import ru.maxbach.onesec.domain.usecase.settings.ObserveUserSettingsUseCase
import ru.maxbach.onesec.presentation.breathe.BreatheActivity
import ru.maxbach.onesec.presentation.breathe.BreatheInitialParams

// test
// ----
// fix navigation of breathe activity
// make service less logic


class BoringService : AccessibilityService() {

  private val observeUserSettings: ObserveUserSettingsUseCase by inject()
  private var currentUserSettings = UserSettings.DEFAULT

  private val isAppSystem: IsAppSystemUseCase by inject()
  private val getForbiddenApp: GetForbiddenAppUseCase by inject()

  private val serviceJob = Job()
  private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

  private var lastEventPackage: CharSequence? = null

  override fun onCreate() {
    super.onCreate()
    serviceScope.launch {
      observeUserSettings()
        .collect { currentUserSettings = it }
    }

    toast("START_SERVICE")
  }

  override fun onAccessibilityEvent(event: AccessibilityEvent) {
    if (event.packageName != lastEventPackage) {
      toast(event.packageName)

      if (isAppSystem(event.packageName).not()) {
        if (lastEventPackage != BuildConfig.APPLICATION_ID) {
          val forbiddenApp = getForbiddenApp(event.packageName, currentUserSettings)

          if (forbiddenApp != null) {
            serviceScope.launch {
              delay(currentUserSettings.openBreatheDelayDuration)
              launchBreatheActivity(forbiddenApp)
            }
          }
        }
        lastEventPackage = event.packageName
      }
    }
  }

  override fun onInterrupt() {
    toast("INTERRUPT SERIVCE")
  }

  override fun onDestroy() {
    super.onDestroy()
    toast("STOP SERIVCE")
    serviceJob.cancel()
  }

  private fun launchBreatheActivity(forbiddenApp: MobileApp) {
    startActivity(
      Intent(applicationContext, BreatheActivity::class.java).also {
        it.putExtras(BreatheActivity.createArgs(BreatheInitialParams(forbiddenApp)))
        it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
      }
    )
  }

  private fun toast(text: CharSequence) {
    if (BuildConfig.DEBUG) {
      Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
  }

}
