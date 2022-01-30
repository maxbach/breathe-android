package ru.maxbach.onesec.domain.repo

import kotlinx.coroutines.flow.Flow
import ru.maxbach.onesec.domain.models.UserSettings

interface UserSettingsRepository {
  suspend fun set(settings: UserSettings)
  suspend fun get(): UserSettings
  fun observe(): Flow<UserSettings>
}
