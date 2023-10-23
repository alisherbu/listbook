package kaa.alisherbu.feature.player.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import kaa.alisherbu.feature.player.component.PlayerComponent
import kaa.alisherbu.feature.player.store.PlayerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(component: PlayerComponent) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Player")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        }) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val (playerController) = createRefs()

            PlayerController(modifier = Modifier.constrainAs(playerController) {
                width = Dimension.matchParent
            })
            createVerticalChain(playerController)
        }
    }
}

@Preview(device = Devices.NEXUS_5)
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(PreviewPlayerComponent())
}

private class PreviewPlayerComponent : PlayerComponent {
    override val state: StateFlow<PlayerState> = MutableStateFlow(PlayerState())

}