package kaa.alisherbu.listbook.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kaa.alisherbu.listbook.feature.home.component.HomeComponent

@Composable
fun HomeScreen(component:HomeComponent) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().background(Color.Magenta)
    ) {
        Text(
            text = "Hello",
            fontSize = 30.sp
        )
    }
}
