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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.educational_practice.ui.theme.EducationalpracticeTheme
import kotlinx.coroutines.selects.RegistrationFunction

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.Authorization.route) {
                composable(Routes.Authorization.route) {
                    AuthorizationScreen(navController)
                }
                composable(Routes.Registration.route) {
                    RegistrationScreen(navController)
                }
            }

        }
    }
}

sealed class Routes(val route: String) {
    object Authorization : Routes("authorization")
    object Registration : Routes("registration")
}


@Composable
fun RegistrationScreen(navController: NavController) {
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
                    navController.navigate(Routes.Authorization.route)
                    /*val intent = Intent(context, Financial::class.java)
                    context.startActivity(intent)*/
                },
                fontSize = 24.sp)
        }
        Button(onClick = {
            //здесь будет ввод данных
            navController.navigate(Routes.Authorization.route)
        },
            modifier = Modifier.padding(top=20.dp).align(Alignment.CenterHorizontally).width(150.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFB1A5B8)))
        {
            Text("Send")
        }
    }
}

@Composable
fun AuthorizationScreen(navController: NavController){
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
                    navController.navigate(Routes.Registration.route)
                    /*val intent = Intent(context, Financial::class.java)
                    context.startActivity(intent)*/
                },
                fontSize = 24.sp)
        }
        Button(onClick = {
            //здесь будет проверка данных
            val intent = Intent(context, Financial::class.java)
            context.startActivity(intent)
        },
            modifier = Modifier.padding(top=20.dp).align(Alignment.CenterHorizontally).width(150.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFB1A5B8)))
        {
            Text("Send")
        }
    }
}