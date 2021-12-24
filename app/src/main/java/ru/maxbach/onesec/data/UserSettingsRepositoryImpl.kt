package ru.maxbach.onesec.data

import android.content.Context
import kotlinx.coroutines.flow.first
import ru.maxbach.onesec.data.proto.settingsDataStore
import ru.maxbach.onesec.domain.models.MobileApp
import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.repo.UserSettingsRepository
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class UserSettingsRepositoryImpl(
  private val context: Context
) : UserSettingsRepository {

  override suspend fun set(settings: UserSettings) {
    context.settingsDataStore.updateData {
      it.newBuilderForType()
        .addAllAppsToBreathe(settings.appsToBreathe.map { it.id.id })
        .setBreatheDurationInSec(settings.breatheDuration.inWholeSeconds.toInt())
        .setOpenBreatheDelayInSec(settings.openBreatheDelayDuration.inWholeSeconds.toInt())
        .build()
    }
  }

  override suspend fun get(): UserSettings {
    return context.settingsDataStore.data.first().let { dto ->
      UserSettings(
        breatheDuration = dto.breatheDurationInSec.toDuration(DurationUnit.SECONDS),
        openBreatheDelayDuration = dto.openBreatheDelayInSec.toDuration(DurationUnit.SECONDS),
        appsToBreathe = dto.appsToBreatheList.mapNotNull { appToBreatheId ->
          MobileApp.values().find { it.id.id == appToBreatheId }
        }
      )
    }
  }

}
