package ru.maxbach.onesec.presentation.breathe

sealed class BreatheViewAction {
  object CloseBreathe : BreatheViewAction()
  object CloseApp : BreatheViewAction()
}
