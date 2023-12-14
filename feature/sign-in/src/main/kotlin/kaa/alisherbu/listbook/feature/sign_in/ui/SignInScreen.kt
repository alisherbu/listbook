package kaa.alisherbu.listbook.feature.sign_in.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kaa.alisherbu.listbook.core.resource.R
import kaa.alisherbu.listbook.core.shared.theme.Orange
import kaa.alisherbu.listbook.feature.sign_in.component.SignInComponent
import kaa.alisherbu.listbook.feature.sign_in.store.SignInState

@Composable
fun SignInScreen(component: SignInComponent) {
    val state by component.state.collectAsState()
    Scaffold(
        topBar = {
            SignInTopAppBar(
                onBackClick = component::onBackClicked,
            )
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) {
        SignInContent(
            state = state,
            onEmailTextChange = component::onEmailTextChanged,
            onPasswordTextChange = component::onPasswordTextChanged,
            onLogInClick = component::onLogInClicked,
            modifier = Modifier.padding(it),
        )
    }
}

@Composable
private fun SignInContent(
    state: SignInState,
    onEmailTextChange: (String) -> Unit,
    onPasswordTextChange: (String) -> Unit,
    onLogInClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Orange)
            .padding(horizontal = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
        ) {
            SignInTextField(
                value = state.email,
                hintText = "Email",
                keyboardType = KeyboardType.Email,
                onValueChange = onEmailTextChange,
            )
            SignInTextField(
                value = state.password,
                hintText = "Password",
                keyboardType = KeyboardType.Password,
                onValueChange = onPasswordTextChange,
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp),
        ) {
            Button(
                modifier = Modifier,
                onClick = onLogInClick,
                colors = buttonColors(
                    containerColor = Color.Black,
                ),
            ) {
                Text(
                    text = "Log in",
                    fontSize = 18.sp,
                )
            }
            Text(
                text = "or",
                color = Color.White,
                fontSize = 18.sp,
            )
            Row {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook_round_icon),
                        contentDescription = "Signup by Facebook",
                        modifier = Modifier.background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp),
                        ),
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_round_icon),
                        contentDescription = "Signup by Facebook",
                        modifier = Modifier.background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp),
                        ),
                    )
                }
            }
        }
    }
}
