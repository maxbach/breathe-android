package ru.maxbach.onesec.presentation.settings

import ru.maxbach.onesec.domain.models.MobileAppId

sealed class SettingsViewState {
  object Uninitialized : SettingsViewState()

  data class Regular(
    val breatheDurationInSec: Int,
    val breatheDurationMaxInSec: Int,
    val breatheOpenDelayInSec: Int,
    val breatheOpenDelayMaxInSec: Int,
    val apps: List<MobileAppViewState>
  ) : SettingsViewState()
}

data class MobileAppViewState(
  val id: MobileAppId,
  val name: String,
  val checked: Boolean
)
