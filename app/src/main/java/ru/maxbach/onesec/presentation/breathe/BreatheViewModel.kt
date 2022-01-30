package ru.maxbach.onesec.presentation.breathe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.maxbach.onesec.domain.usecase.settings.GetUserSettingsUseCase

class BreatheViewModel(
  private val initialParams: BreatheInitialParams,
  private val getUserSettings: GetUserSettingsUseCase,
) : ViewModel() {

  private val _state: MutableStateFlow<BreatheViewState> =
    MutableStateFlow(BreatheViewState())
  val state: Flow<BreatheViewState> = _state

  private val _viewEvent: MutableSharedFlow<BreatheViewEvent> =
    MutableSharedFlow(replay = 0)
  val viewEvent: Flow<BreatheViewEvent> = _viewEvent

  init {
    viewModelScope.launch {
      delay(getUserSettings().breatheDuration)
      _state.emit(BreatheViewState(buttons = ButtonsViewState.Visible(initialParams.forbiddenApp.appName)))
    }
  }

  fun handleAction(action: BreatheViewAction) {
    viewModelScope.launch {
      when (action) {
        BreatheViewAction.CloseApp -> _viewEvent.emit(BreatheViewEvent.CloseBreatheAndLastApps)
        BreatheViewAction.CloseBreathe -> _viewEvent.emit(BreatheViewEvent.CloseBreathe)
      }
    }
  }

}
