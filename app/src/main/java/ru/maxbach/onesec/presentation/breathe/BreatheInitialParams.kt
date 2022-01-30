package ru.maxbach.onesec.presentation.breathe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.maxbach.onesec.domain.models.MobileApp

@Parcelize
data class BreatheInitialParams(
  val forbiddenApp: MobileApp
) : Parcelable
