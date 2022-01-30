package ru.maxbach.onesec.presentation.settings

import ru.maxbach.onesec.domain.models.MobileAppId

sealed class SettingsViewAction {
  data class BreatheDurationChanged(val newValue: Int) : SettingsViewAction()
  data class BreatheOpenDelayChanged(val newValue: Int) : SettingsViewAction()
  data class ChosenAppsChanged(val changedAppId: MobileAppId) : SettingsViewAction()
  object SaveButtonClicked : SettingsViewAction()
  object OpenSystemSettingsClicked : SettingsViewAction()
}
