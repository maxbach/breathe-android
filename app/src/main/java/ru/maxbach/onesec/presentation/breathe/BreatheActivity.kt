package ru.maxbach.onesec.presentation.breathe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BreatheActivity : ComponentActivity() {

  private var initialParams: BreatheInitialParams? = null

  private val viewModel: BreatheViewModel by viewModel { parametersOf(initialParams) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    initialParams = intent.getParcelableExtra(INITIAL_PARAMS_ARGS_KEY)
      ?: throw IllegalStateException("Breathe activity must be opened with initial params")

    setContent {
      val viewState = viewModel
        .state
        .collectAsState(initial = BreatheViewState())
        .value

      Screen(viewState = viewState)
    }

    lifecycleScope.launchWhenResumed {
      viewModel
        .viewEvent
        .collect(this@BreatheActivity::handleViewEvent)
    }

  }

  override fun onBackPressed() {}

  private fun handleViewEvent(viewEvent: BreatheViewEvent) {
    when (viewEvent) {
      BreatheViewEvent.CloseBreathe -> finish()
      BreatheViewEvent.CloseBreatheAndLastApps -> {
        startActivity(
          Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_HOME)
            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
      }
    }
  }

  @Composable
  @Preview(device = Devices.PIXEL_3A)
  private fun PreviewScreen() {
    Screen(
      viewState = BreatheViewState(ButtonsViewState.Visible("Telegram"))
    )
  }

  @Composable
  private fun Screen(viewState: BreatheViewState) {
    MaterialTheme {
      Column {
        Text(
          modifier = Modifier.padding(16f.dp),
          text = "Just breathe and calm down",
          style = MaterialTheme.typography.h2
        )
        if (viewState.buttons is ButtonsViewState.Visible) {
          Button(
            modifier = Modifier.padding(16f.dp),
            onClick = { viewModel.handleAction(BreatheViewAction.CloseBreathe) }) {
            Text(text = "Close Breathe and return to ${viewState.buttons.appName}")
          }
          Button(
            modifier = Modifier.padding(16f.dp),
            onClick = { viewModel.handleAction(BreatheViewAction.CloseApp) }) {
            Text(text = "Close Breathe and ${viewState.buttons.appName}")
          }
        }
      }
    }
  }

  companion object {
    fun createArgs(initialParams: BreatheInitialParams) = bundleOf(
      INITIAL_PARAMS_ARGS_KEY to initialParams
    )

    private const val INITIAL_PARAMS_ARGS_KEY = "initial_params"
  }
}
