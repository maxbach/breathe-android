package ru.maxbach.onesec.presentation.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.maxbach.onesec.BuildConfig
import ru.maxbach.onesec.domain.models.MobileApp
import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.usecase.GetForbiddenAppUseCase
import ru.maxbach.onesec.domain.usecase.IsAppSystemUseCase
import ru.maxbach.onesec.domain.usecase.settings.ObserveUserSettingsUseCase
import kotlin.time.Duration

class BoringServicePresenter(
  private val observeUserSettings: ObserveUserSettingsUseCase,
  private val isAppSystem: IsAppSystemUseCase,
  private val getForbiddenApp: GetForbiddenAppUseCase,
  private val applicationScope: CoroutineScope
) {

  private val _viewEvent: MutableSharedFlow<BoringServiceEvent> = MutableSharedFlow(replay = 0)
  val viewEvent: Flow<BoringServiceEvent> = _viewEvent

  private var currentUserSettings = UserSettings.DEFAULT
  private var lastAppPackage: CharSequence? = null

  init {
    applicationScope.launch {
      observeUserSettings()
        .collect { currentUserSettings = it }
    }
  }

  fun handleAction(action: BoringServiceAction) {
    when (action) {
      is BoringServiceAction.NewEvent -> handleNewEvent(action)
    }
  }

  private fun handleNewEvent(action: BoringServiceAction.NewEvent) {
    val openedAppPackageName = action.packageName

    applicationScope.launch {
      if (openedAppPackageName != lastAppPackage) {
        if (isAppSystem(openedAppPackageName).not()) {
          handleNonSystemEvent(openedAppPackageName)
        } else {
          saveLastOpenedAppPackage(openedAppPackageName)
        }
      }
    }
  }

  private suspend fun handleNonSystemEvent(openedAppPackageName: CharSequence) {
    _viewEvent.emit(BoringServiceEvent.ShowToastMessageForDebug(openedAppPackageName))
    if (lastAppPackage != BuildConfig.APPLICATION_ID) {
      getForbiddenApp(openedAppPackageName, currentUserSettings)
        ?.let { openBreatheScreen(it) }
    }
    saveLastOpenedAppPackage(openedAppPackageName)
  }

  private suspend fun openBreatheScreen(forbiddenApp: MobileApp) {
    if (currentUserSettings.openBreatheDelayDuration == Duration.ZERO) {
      _viewEvent.emit(BoringServiceEvent.OpenBreatheScreen(forbiddenApp))
    } else {
      delay(currentUserSettings.openBreatheDelayDuration)
      if (lastAppPackage in forbiddenApp.packages) {
        _viewEvent.emit(BoringServiceEvent.OpenBreatheScreen(forbiddenApp))
      }
    }
  }

  private fun saveLastOpenedAppPackage(appPackage: CharSequence) {
    lastAppPackage = appPackage
  }
}
