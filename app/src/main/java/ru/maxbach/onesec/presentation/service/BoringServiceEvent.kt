package ru.maxbach.onesec.presentation.service

import ru.maxbach.onesec.domain.models.MobileApp

sealed class BoringServiceEvent {
  data class OpenBreatheScreen(val forbiddenApp: MobileApp) : BoringServiceEvent()
  data class ShowToastMessageForDebug(val message: CharSequence) : BoringServiceEvent()
}
