package ru.maxbach.onesec.presentation.breathe

sealed class BreatheViewEvent {
  object CloseBreathe : BreatheViewEvent()
  object CloseBreatheAndLastApps : BreatheViewEvent()
}
