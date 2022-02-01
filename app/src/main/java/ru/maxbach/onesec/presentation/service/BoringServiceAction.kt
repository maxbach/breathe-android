package ru.maxbach.onesec.presentation.service

sealed class BoringServiceAction {
  data class NewEvent(val packageName: CharSequence) : BoringServiceAction()
}
