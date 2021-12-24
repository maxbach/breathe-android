package ru.maxbach.onesec.di

import org.koin.dsl.module
import ru.maxbach.onesec.data.UserSettingsRepositoryImpl
import ru.maxbach.onesec.domain.repo.UserSettingsRepository

val appModule = module {
  single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }
}
