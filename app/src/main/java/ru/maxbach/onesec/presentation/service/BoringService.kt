package ru.maxbach.onesec.presentation.service

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import ru.maxbach.onesec.BuildConfig
import ru.maxbach.onesec.domain.models.MobileApp
import ru.maxbach.onesec.presentation.breathe.BreatheActivity
import ru.maxbach.onesec.presentation.breathe.BreatheInitialParams

class BoringService : AccessibilityService() {

  private val serviceJob = Job()
  private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

  private val presenter: BoringServicePresenter by inject()

  override fun onCreate() {
    super.onCreate()
    serviceScope.launch {
      presenter.viewEvent
        .collect { event ->
          when (event) {
            is BoringServiceEvent.OpenBreatheScreen -> launchBreatheActivity(event.forbiddenApp)
            is BoringServiceEvent.ShowToastMessageForDebug -> showToast(event.message)
          }
        }
    }

    showToast("START_SERVICE")
  }

  override fun onAccessibilityEvent(event: AccessibilityEvent) {
    presenter.handleAction(BoringServiceAction.NewEvent(event.packageName))
  }

  override fun onInterrupt() = Unit

  override fun onDestroy() {
    super.onDestroy()
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

  private fun showToast(text: CharSequence) {
    if (BuildConfig.DEBUG) {
      Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
  }

}
