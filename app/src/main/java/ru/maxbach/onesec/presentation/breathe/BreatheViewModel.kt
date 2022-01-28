package ru.maxbach.onesec.presentation.breathe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.maxbach.onesec.domain.usecase.GetUserSettingsUseCase

class BreatheViewModel(
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
      // TODO: add initial params
      _state.emit(BreatheViewState(buttons = ButtonsViewState.Visible("Telegram")))
    }
  }

  fun handleAction(action: BreatheViewAction) {
    when (action) {
      BreatheViewAction.CloseApp -> _viewEvent.tryEmit(BreatheViewEvent.CloseBreathe)
      BreatheViewAction.CloseBreathe -> _viewEvent.tryEmit(BreatheViewEvent.CloseBreatheAndLastApps)
    }
  }

}
