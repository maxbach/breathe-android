package ru.maxbach.onesec.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.maxbach.onesec.domain.models.MobileAppId
import ru.maxbach.onesec.domain.models.UserSettings
import ru.maxbach.onesec.domain.usecase.GetUserSettingsUseCase
import ru.maxbach.onesec.domain.usecase.SetUserSettingsUseCase
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class SettingsViewModel(
  private val getUserSettings: GetUserSettingsUseCase,
  private val setUserSettings: SetUserSettingsUseCase
) : ViewModel() {

  private val _state: MutableStateFlow<SettingsViewState> =
    MutableStateFlow(SettingsViewState.Uninitialized)
  val state: Flow<SettingsViewState> = _state

  private val currentRegularState: SettingsViewState.Regular
    get() = _state.value as SettingsViewState.Regular

  init {
    getSettings()
  }

  fun handleAction(action: SettingsViewAction) {
    viewModelScope.launch {
      when (action) {
        is SettingsViewAction.BreatheDurationChanged -> {
          _state.emit(currentRegularState.copy(breatheDurationInSec = action.newValue))
        }
        is SettingsViewAction.BreatheOpenDelayChanged -> {
          _state.emit(currentRegularState.copy(breatheOpenDelayInSec = action.newValue))
        }
        is SettingsViewAction.ChosenAppsChanged -> {
          _state.emit(currentRegularState.switchChosenApp(action.changedAppId))
        }
        SettingsViewAction.SaveButtonClicked -> saveSettings()
      }
    }
  }

  private fun saveSettings() {
    viewModelScope.launch {
      setUserSettings(
        with(currentRegularState) {
          UserSettings(
            breatheDuration = breatheDurationInSec.toDuration(DurationUnit.SECONDS),
            openBreatheDelayDuration = breatheOpenDelayInSec.toDuration(DurationUnit.SECONDS),
            chosenAppIds = apps.filter(MobileAppViewState::checked).map(MobileAppViewState::id)
          )
        }
      )
    }
  }

  private fun getSettings() {
    viewModelScope.launch {
      val settings = getUserSettings()

      _state.emit(SettingsViewStateMapper.map(settings))
    }
  }

  private fun SettingsViewState.Regular.switchChosenApp(
    changeAppId: MobileAppId
  ): SettingsViewState.Regular = copy(
    apps = apps.map { mobileApp ->
      if (mobileApp.id == changeAppId) {
        mobileApp.copy(checked = !mobileApp.checked)
      } else {
        mobileApp
      }
    }
  )
}
