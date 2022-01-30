package ru.maxbach.onesec.presentation.settings

sealed class SettingsViewEvent {
  object OpenSystemSettings : SettingsViewEvent()
}
