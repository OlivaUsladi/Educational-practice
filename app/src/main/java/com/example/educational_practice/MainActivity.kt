package com.example.educational_practice

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.RegistrationFunction
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.SplashScreen.route) {
                composable(Routes.Authorization.route) {
                    AuthorizationScreen(navController)
                }
                composable(Routes.Registration.route) {
                    RegistrationScreen(navController)
                }
                composable(Routes.SplashScreen.route){
                    SplashScreen(navController)
                }
            }
        }
    }
}

sealed class Routes(val route: String) {
    object Authorization : Routes("authorization")
    object Registration : Routes("registration")
    object SplashScreen: Routes("SplashScreen")
}


@Composable
fun RegistrationScreen(navController: NavController) {
    var login = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var email = remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    var db = App.instance
    var userDao = db.userDao()

    var flag1 = remember { mutableStateOf(0) }

    //flag.value=1
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
            TextField(modifier = Modifier.padding(top = 150.dp).align(Alignment.Center).height(60.dp),
                value = email.value,
                onValueChange = { email.value = it },
                placeholder = {
                    Text(
                        text = "Введите email",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top = 25.dp).align(Alignment.Center).height(60.dp),
                value = login.value,
                onValueChange = { login.value = it },
                placeholder = {
                    Text(
                        text = "Введите login",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)) {
            TextField(modifier = Modifier.padding(top=25.dp).align(Alignment.Center).height(60.dp),
                value = password.value,
                onValueChange = {password.value = it},
                placeholder = { Text(text = "Введите пароль", fontSize = 18.sp, textAlign = TextAlign.Center) })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)){
            Text(text = "Авторизация",
                color = Color(0xFFA47676),
                modifier = Modifier.align(Alignment.Center).padding(top=40.dp).clickable(){
                    navController.navigate(Routes.Authorization.route)

                },
                fontSize = 24.sp)
        }
        Button(onClick = {

            CoroutineScope(Dispatchers.IO).launch {
                Log.i("here", "добавляем в бд user")
                if (userDao.getUsersByEmail(email.value) == null && userDao.getUsersByLogin(login.value) == null) {
                    userDao.insertUser(User(login = login.value, email = email.value, password = password.value))
                    flag1.value = 1
                } else {
                    snackbarHostState.showSnackbar("Пользователь с таким логином или почтой уже существует")
                }

                if (flag1.value == 1) {
                    withContext(Dispatchers.Main) {
                        navController.navigate(Routes.Authorization.route)
                    }
                }
            }

        },
            modifier = Modifier.padding(top=20.dp).align(Alignment.CenterHorizontally).width(150.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFB1A5B8)))
        {
            Text("Зарегистрироваться")
        }
    }
}


var chooseid =  mutableStateOf(0L)

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AuthorizationScreen(navController: NavController){
    val context = LocalContext.current
    var login = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var errorFlag = remember{mutableStateOf(false)}
    //val userList: Array<String> = stringArrayResource(id =R.array.users )
    val userList = remember { mutableListOf<User>() }

    var db = App.instance
    var userDao = db.userDao()

    /*if (flag.value==0){
        users.addAll(userList)
    }*/
    CoroutineScope(Dispatchers.IO).launch {
        userList.addAll(userDao.getAllUsers())
    }


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
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).padding(top = 150.dp)) {
            TextField(modifier = Modifier.align(Alignment.Center).height(60.dp),
                value = login.value,
                onValueChange = { login.value = it },
                isError = errorFlag.value,
                placeholder = {
                    Text(
                        text = "Введите login/email",
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center
                    )
                })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally).padding(top=35.dp)) {
            TextField(modifier = Modifier.align(Alignment.Center).height(60.dp),
                value = password.value,
                onValueChange = {password.value = it},
                isError = errorFlag.value,
                placeholder = { Text(text = "Введите пароль",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center) })
        }
        Box(Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)){
            Text(text = "Регистрация",
                color = Color(0xFFA47676),
                modifier = Modifier.align(Alignment.Center).padding(top=40.dp).clickable(){
                    navController.navigate(Routes.Registration.route)
                },
                fontSize = 24.sp)
        }
        Button(onClick = {

            if (userList.any { (it.login==login.value && it.password == password.value) ||
                        (it.email==login.value && it.password == password.value)}) {
                CoroutineScope(Dispatchers.IO).launch {

                    userDao.getUsersByEmail(login.value)?.let { user ->
                        chooseid.value = user.id
                    }
                    userDao.getUsersByLogin(login.value)?.let { user ->
                        chooseid.value = user.id
                    }


                }

                val intent = Intent(context, Financial::class.java)
                context.startActivity(intent)
            }else{
                errorFlag.value=true
            }
        },
            modifier = Modifier.padding(top=20.dp).align(Alignment.CenterHorizontally).width(150.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFFB1A5B8)))
        {
            Text("Войти")
        }
    }
}

@Composable
fun SplashScreen(navController: NavController){
    LaunchedEffect(key1 = true) {
        delay(2500)
        navController.navigate(Routes.Authorization.route){
            popUpTo(0)
        }
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){
        Column {
            Image(painter = painterResource(R.drawable.img),
                contentDescription = "logo",
                modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.height(7.dp))
            Text(text = "The Road to adulthood",
                color = Color(0xFFA47676),
                fontSize = 32.sp)
        }
    }
}