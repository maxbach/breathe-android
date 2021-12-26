package ru.maxbach.onesec.presentation.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.maxbach.onesec.domain.models.MobileAppId

class SettingsActivity : ComponentActivity() {

  private val viewModel: SettingsViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val viewState = viewModel
        .state
        .collectAsState(initial = SettingsViewState.Uninitialized)
        .value

      Screen(viewState = viewState)
    }
  }

  @Composable
  @Preview(device = Devices.PIXEL_3A)
  private fun PreviewScreen() {
    Screen(
      viewState = SettingsViewState.Regular(
        breatheDurationInSec = 5,
        breatheDurationMaxInSec = 100,
        breatheOpenDelayInSec = 10,
        breatheOpenDelayMaxInSec = 100,
        apps = listOf(
          MobileAppViewState(id = MobileAppId(id = ""), name = "App1", checked = false),
          MobileAppViewState(id = MobileAppId(id = ""), name = "App2", checked = false),
          MobileAppViewState(id = MobileAppId(id = ""), name = "App3", checked = true),
          MobileAppViewState(id = MobileAppId(id = ""), name = "App4", checked = false),
        )
      )
    )
  }

  @Composable
  private fun Screen(viewState: SettingsViewState) {
    MaterialTheme {
      when (viewState) {
        is SettingsViewState.Regular -> RegularScreen(viewState = viewState)
        SettingsViewState.Uninitialized -> UninitializedScreen()
      }
    }
  }

  @Composable
  private fun UninitializedScreen() {
    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
  }

  @Composable
  private fun RegularScreen(viewState: SettingsViewState.Regular) {
    Column {
      Text(text = "Settings", style = MaterialTheme.typography.h2)
      SliderWithHeader(
        header = "Breathe duration",
        currentValue = viewState.breatheDurationInSec,
        maxValue = viewState.breatheDurationMaxInSec,
        onValueChange = { viewModel.handleAction(SettingsViewAction.BreatheDurationChanged(it)) }
      )

      SliderWithHeader(
        header = "Breathe open delay",
        currentValue = viewState.breatheOpenDelayInSec,
        maxValue = viewState.breatheOpenDelayMaxInSec,
        onValueChange = { viewModel.handleAction(SettingsViewAction.BreatheOpenDelayChanged(it)) }
      )
      Text(
        modifier = Modifier.padding(8f.dp),
        text = "Apps to Breathe",
        style = MaterialTheme.typography.h4
      )
      viewState.apps.forEach {
        MobileAppCheckbox(appViewState = it)
      }
      Row(
        Modifier
          .padding(8f.dp)
          .align(CenterHorizontally)
      ) {
        Button(onClick = { viewModel.handleAction(SettingsViewAction.SaveButtonClicked) }) {
          Text(text = "Save settings")
        }
      }
    }
  }

  @Composable
  private fun MobileAppCheckbox(
    appViewState: MobileAppViewState
  ) {
    Row(modifier = Modifier.padding(all = 8f.dp)) {
      Checkbox(
        checked = appViewState.checked,
        onCheckedChange = { viewModel.handleAction(SettingsViewAction.ChosenAppsChanged(appViewState.id)) }
      )
      Text(text = appViewState.name, modifier = Modifier.padding(horizontal = 8f.dp))
    }
  }

  @Composable
  private fun SliderWithHeader(
    header: String,
    currentValue: Int,
    maxValue: Int,
    onValueChange: (Int) -> Unit
  ) {
    Column(modifier = Modifier.padding(all = 8f.dp)) {
      Text(
        text = "$header ($currentValue)",
        style = MaterialTheme.typography.h4
      )
      Slider(
        value = currentValue.toFloat(),
        valueRange = (0f..maxValue.toFloat()),
        steps = 20,
        onValueChange = { onValueChange(it.toInt()) }
      )
    }
  }
}
