package kaa.alisherbu.listbook.feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kaa.alisherbu.listbook.feature.home.component.HomeComponent

@Composable
fun HomeScreen(component: HomeComponent) {
    val state by component.state.collectAsState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 24.dp)
    ) {
        Text(
            text = "Home", style = TextStyle(
                fontSize = 32.sp
            )
        )
        LazyColumn(
            modifier = Modifier
                .weight(1F)
                .padding(top = 12.dp)
        ) {
            items(state.audioBooks) {
                AudioBookItem(audioBook = it, component::onAudioBookClick)
            }
        }
    }
}
