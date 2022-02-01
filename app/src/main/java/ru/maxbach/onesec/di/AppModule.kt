package ru.maxbach.onesec.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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
import ru.maxbach.onesec.presentation.service.BoringServicePresenter
import ru.maxbach.onesec.presentation.settings.SettingsViewModel

val appModule = module {
  single<UserSettingsRepository> { UserSettingsRepositoryImpl(get()) }

  single { CoroutineScope(SupervisorJob() + Dispatchers.Main) }

  single { GetUserSettingsUseCase(get()) }
  single { SetUserSettingsUseCase(get()) }
  single { ObserveUserSettingsUseCase(get()) }

  single { IsAppSystemUseCase() }
  single { GetForbiddenAppUseCase() }

  single { BoringServicePresenter(get(), get(), get(), get()) }

  viewModel { SettingsViewModel(get(), get()) }
  viewModel { BreatheViewModel(it.get(), get()) }
}
