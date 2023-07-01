package kaa.alisherbu.listbook.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kaa.alisherbu.listbook.R
import kaa.alisherbu.listbook.ui.theme.Hint
import kaa.alisherbu.listbook.ui.theme.Orange

@Composable
fun SignupScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Orange)
            .padding(horizontal = 16.dp)
            .statusBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = {
//                    navigation.pop()
                },
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Registration",
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White,

            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp)
        ) {
            var name by remember { mutableStateOf("") }
            var surname by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            SignupTextField(
                value = name,
                hintText = "Name",
                onValueChange = { text -> name = text }
            )
            SignupTextField(
                value = surname,
                hintText = "Surname",
                onValueChange = { text -> surname = text }
            )
            SignupTextField(
                value = email,
                hintText = "Email",
                keyboardType = KeyboardType.Email,
                onValueChange = { text -> email = text }
            )
            SignupTextField(
                value = password,
                hintText = "Password",
                keyboardType = KeyboardType.Password,
                onValueChange = { text -> password = text }
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        ) {
            Button(
                modifier = Modifier,
                onClick = { },
                colors = buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text(
                    text = "Create account",
                    fontSize = 18.sp
                )
            }
            Text(
                text = "or",
                color = Color.White,
                fontSize = 18.sp
            )
            Row {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.facebook_round_icon),
                        contentDescription = "Signup by Facebook",
                        modifier = Modifier.background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_round_icon),
                        contentDescription = "Signup by Facebook",
                        modifier = Modifier.background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp)
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupTextField(
    value: String,
    hintText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = value,
        onValueChange = { text -> if (text.length < 30) onValueChange(text) },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 18.sp,
        ),
        placeholder = {
            Text(
                text = hintText,
                fontSize = 18.sp,
                color = Hint
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Orange,
            focusedIndicatorColor = Color.White,
            unfocusedIndicatorColor = Color.White,
            cursorColor = Color.White
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.Sentences,
            imeAction = ImeAction.Next,
            keyboardType = keyboardType
        ),
        singleLine = true,
        maxLines = 1,
    )
}
