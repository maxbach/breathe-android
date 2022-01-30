package ru.maxbach.onesec.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.maxbach.onesec.data.UserSettingsRepositoryImpl
import ru.maxbach.onesec.domain.repo.UserSettingsRepository
import ru.maxbach.onesec.domain.usecase.GetForbiddenAppUseCase
import ru.maxbach.onesec.domain.usecase.IsAppSystemUseCase
import ru.maxbach.onesec.domain.usecase.settings.GetUserSettingsUseCase
import ru.maxbach.onesec.domain.usecase.settings.ObserveUserSettingsUseCase
import ru.maxbach.onesec.domain.usecase.settings.SetUserSettingsUseCase
import ru.maxbach.onesec.presentation.breathe.BreatheViewModel
import ru.maxbach.onesec.presentation.settings.SettingsViewModel

val appModule = module {
  single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }

  single { GetUserSettingsUseCase(get()) }
  single { SetUserSettingsUseCase(get()) }
  single { ObserveUserSettingsUseCase(get()) }

  single { IsAppSystemUseCase() }
  single { GetForbiddenAppUseCase() }

  viewModel { SettingsViewModel(get(), get()) }
  viewModel { BreatheViewModel(it.get(), get()) }
}
