package kaa.alisherbu.listbook.feature.sign_in.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kaa.alisherbu.listbook.core.util.theme.Orange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignInTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Sign in",
                color = Color.White,
            )
        },
        modifier = modifier,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Orange,
        ),
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(top = 10.dp),
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }
        },
    )
}