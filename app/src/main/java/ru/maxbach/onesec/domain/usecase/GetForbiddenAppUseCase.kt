package ru.maxbach.onesec.domain.usecase

import ru.maxbach.onesec.domain.models.MobileApp
import ru.maxbach.onesec.domain.models.UserSettings

class GetForbiddenAppUseCase {
  operator fun invoke(
    appPackageName: CharSequence,
    userSettings: UserSettings
  ): MobileApp? = userSettings
    .chosenAppIds
    .mapNotNull { chosenAppId -> MobileApp.values().find { it.id == chosenAppId } }
    .find { appPackageName in it.packages }
}
