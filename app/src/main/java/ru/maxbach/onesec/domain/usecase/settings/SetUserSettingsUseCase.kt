package ru.maxbach.onesec.domain.usecase.settings

import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.repo.UserSettingsRepository

class SetUserSettingsUseCase(
  private val repository: UserSettingsRepository
) {
  suspend operator fun invoke(settings: UserSettings) {
    repository.set(settings)
  }
}
