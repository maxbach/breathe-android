package ru.maxbach.onesec.data

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import ru.maxbach.onesec.data.proto.settingsDataStore
import ru.maxbach.onesec.domain.models.MobileAppId
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
        .addAllAppsToBreathe(settings.chosenAppIds.map(MobileAppId::id))
        .setBreatheDurationInSec(settings.breatheDuration.inWholeSeconds.toInt())
        .setOpenBreatheDelayInSec(settings.openBreatheDelayDuration.inWholeSeconds.toInt())
        .build()
    }
  }

  override suspend fun get(): UserSettings = context.settingsDataStore.data.first().mapToDomain()

  override fun observe(): Flow<UserSettings> =
    context.settingsDataStore.data.map { it.mapToDomain() }

  private fun UserSettingsDto.mapToDomain() = UserSettings(
    breatheDuration = breatheDurationInSec.toDuration(DurationUnit.SECONDS),
    openBreatheDelayDuration = openBreatheDelayInSec.toDuration(DurationUnit.SECONDS),
    chosenAppIds = appsToBreatheList.map(::MobileAppId)
  )

}
