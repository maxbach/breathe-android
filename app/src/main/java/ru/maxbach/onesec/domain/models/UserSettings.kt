package ru.maxbach.onesec.domain.models

import kotlin.time.Duration

data class UserSettings(
  val breatheDuration: Duration,
  val openBreatheDelayDuration: Duration,
  val appsToBreathe: List<MobileApp>
)
