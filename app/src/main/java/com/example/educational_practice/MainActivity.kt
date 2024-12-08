package com.example.educational_practice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educational_practice.ui.theme.EducationalpracticeTheme
import kotlinx.coroutines.selects.RegistrationFunction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationScreen()
        }
    }
}

@Composable
fun RegistrationScreen(){
    var login = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    val context = LocalContext.current
    Column (modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Image(painter = painterResource(R.drawable.img),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top=134.dp)
                .width(144.dp)
                .height(169.dp)
                .align(Alignment.CenterHorizontally)
        )
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            Text(
                text = "The Road to adulthood",
                color = Color(0xFFA47676),
                modifier = Modifier.align(Alignment.Center),
                fontSize = 32.sp
            )
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top = 150.dp).align(Alignment.Center).height(40.dp),
                value = email.value,
                onValueChange = { email.value = it },
                label = {
                    Text(
                        text = "Enter email",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top = 50.dp).align(Alignment.Center).height(40.dp),
                value = login.value,
                onValueChange = { login.value = it },
                label = {
                    Text(
                        text = "Enter login",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top=50.dp).align(Alignment.Center).height(40.dp),
                value = password.value,
                onValueChange = {password.value = it},
                label = { Text(text = "Enter password", fontSize = 24.sp, textAlign = TextAlign.Center) })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)){
            Text(text = "Authorization",
                color = Color(0xFFA47676),
                modifier = Modifier.align(Alignment.Center).padding(top=40.dp).clickable(){
                    val intent = Intent(context, Financial::class.java)
                    context.startActivity(intent)
                },
                fontSize = 24.sp)
        }
    }
}

@Composable
fun AuthorizationScreen(){
    val context = LocalContext.current
    var login = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    Column (modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Image(painter = painterResource(R.drawable.img),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top=134.dp)
                .width(144.dp)
                .height(169.dp)
                .align(Alignment.CenterHorizontally)
        )
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            Text(
                text = "The Road to adulthood",
                color = Color(0xFFA47676),
                modifier = Modifier.align(Alignment.Center),
                fontSize = 32.sp
            )
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top = 150.dp).align(Alignment.Center).height(40.dp),
                value = login.value,
                onValueChange = { login.value = it },
                label = {
                    Text(
                        text = "Enter login/email",
                        fontSize = 24.sp,
                        textAlign = TextAlign.Center
                    )
                })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top=50.dp).align(Alignment.Center).height(40.dp),
                value = password.value,
                onValueChange = {password.value = it},
                label = { Text(text = "Enter password", fontSize = 24.sp, textAlign = TextAlign.Center) })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)){
            Text(text = "Registration",
                color = Color(0xFFA47676),
                modifier = Modifier.align(Alignment.Center).padding(top=40.dp).clickable(){
                    val intent = Intent(context, Financial::class.java)
                    context.startActivity(intent)
                },
                fontSize = 24.sp)
        }
    }
}