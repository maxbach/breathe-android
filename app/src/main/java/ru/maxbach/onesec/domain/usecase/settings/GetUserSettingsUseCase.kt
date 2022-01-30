package ru.maxbach.onesec.domain.usecase.settings

import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.repo.UserSettingsRepository

class GetUserSettingsUseCase(
  private val repository: UserSettingsRepository
) {
  suspend operator fun invoke(): UserSettings = repository.get()
}
