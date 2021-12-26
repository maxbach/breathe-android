package ru.maxbach.onesec.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.maxbach.onesec.data.UserSettingsRepositoryImpl
import ru.maxbach.onesec.domain.repo.UserSettingsRepository
import ru.maxbach.onesec.domain.usecase.GetUserSettingsUseCase
import ru.maxbach.onesec.domain.usecase.SetUserSettingsUseCase
import ru.maxbach.onesec.presentation.settings.SettingsViewModel

val appModule = module {
  single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }

  single { GetUserSettingsUseCase(get()) }
  single { SetUserSettingsUseCase(get()) }

  viewModel { SettingsViewModel(get(), get()) }
}
