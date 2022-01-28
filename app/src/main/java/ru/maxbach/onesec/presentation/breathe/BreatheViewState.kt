package ru.maxbach.onesec.presentation.breathe

data class BreatheViewState(
  val buttons: ButtonsViewState = ButtonsViewState.NotVisible
)

sealed class ButtonsViewState {
  object NotVisible : ButtonsViewState()
  data class Visible(val appName: String) : ButtonsViewState()
}
