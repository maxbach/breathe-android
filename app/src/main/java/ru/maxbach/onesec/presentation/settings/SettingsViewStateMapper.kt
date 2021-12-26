package ru.maxbach.onesec.presentation.settings

import ru.maxbach.onesec.domain.models.MobileApp
import ru.maxbach.onesec.domain.models.UserSettings

object SettingsViewStateMapper {
  fun map(userSettings: UserSettings) = with(userSettings) {
    SettingsViewState.Regular(
      breatheDurationInSec = breatheDuration.inWholeSeconds.toInt(),
      breatheDurationMaxInSec = UserSettings.BREATHE_DURATION_IN_SEC_MAX,
      breatheOpenDelayInSec = openBreatheDelayDuration.inWholeSeconds.toInt(),
      breatheOpenDelayMaxInSec = UserSettings.BREATHE_OPEN_DELAY_IN_SEC_MAX,
      apps = MobileApp.values().map { mobileApp ->
        MobileAppViewState(
          id = mobileApp.id,
          name = mobileApp.appName,
          checked = mobileApp.id in chosenAppIds
        )
      }
    )
  }
}
