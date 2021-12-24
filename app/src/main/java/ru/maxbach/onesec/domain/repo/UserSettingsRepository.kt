package ru.maxbach.onesec.domain.repo

import ru.maxbach.onesec.domain.models.UserSettings

interface UserSettingsRepository {
  suspend fun set(settings: UserSettings)
  suspend fun get(): UserSettings
}
