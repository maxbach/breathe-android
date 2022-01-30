package ru.maxbach.onesec.domain.usecase.settings

import kotlinx.coroutines.flow.Flow
import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.repo.UserSettingsRepository

class ObserveUserSettingsUseCase(
  private val repository: UserSettingsRepository
) {
  suspend operator fun invoke(): Flow<UserSettings> = repository.observe()
}
