package ru.maxbach.onesec.domain.models

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class UserSettings(
  val breatheDuration: Duration,
  val openBreatheDelayDuration: Duration,
  val chosenAppIds: List<MobileAppId>
) {
  companion object {
    const val BREATHE_DURATION_IN_SEC_DEFAULT = 5
    const val BREATHE_DURATION_IN_SEC_MAX = 100
    const val BREATHE_OPEN_DELAY_IN_SEC_DEFAULT = 0
    const val BREATHE_OPEN_DELAY_IN_SEC_MAX = 100
    val CHOSEN_APPS_DEFAULT = emptyList<MobileAppId>()

    val DEFAULT = UserSettings(
      breatheDuration = BREATHE_DURATION_IN_SEC_DEFAULT.toDuration(DurationUnit.SECONDS),
      openBreatheDelayDuration = BREATHE_OPEN_DELAY_IN_SEC_DEFAULT.toDuration(DurationUnit.SECONDS),
      chosenAppIds = CHOSEN_APPS_DEFAULT
    )
  }
}
