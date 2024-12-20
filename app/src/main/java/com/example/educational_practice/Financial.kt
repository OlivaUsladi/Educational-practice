package com.example.educational_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.educational_practice.ui.theme.EducationalpracticeTheme

class Financial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Surface() {
                Scaffold (
                    bottomBar = {
                        BottomNavigationBar(navController =
                        navController)
                    },
                    content = {
                            padding -> NavHostContainer(navController
                    = navController, padding = padding)
                    }
                )
            }
        }
    }
}


//############################################################################
//класс для хранения элементов навигации
//############################################################################
data class BottomNavItem(
    val label: String, //название элемента навигации
    val icon: Int, //иконка элемента навигации
    val route:String, //маршрут элемента навигации
)

//############################################################################
//объекты навигации
//############################################################################

object Constants {
    val BottomNavItems = listOf( //cписок объектов навигации
        BottomNavItem(
            label = "Targets",
            icon = R.drawable.icon_targets ,
            route = "route 1" ),
        BottomNavItem(
            label = "Analyze",
            icon = R.drawable.icon_analyze ,
            route = "route 2"
        ),
        BottomNavItem(
            label = "Limits",
            icon = R.drawable.icon_limits,
            route = "route 3"
        )
    )
}

@Composable
fun NavHostContainer(
    navController: NavHostController, //передаем NavHostController
    padding: PaddingValues //передаем отступ
) {
    NavHost(
        navController = navController,
        startDestination = "route 1", //Начальное положение(путь домашнего экрана)
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable("route 1") {
                TargetsScreen()
            }
            composable("route 2") {
                AnalyzeScreen()
            }
            composable("route 3") {
                LimitsScreen()
            }
        })
}


//============================================================================
//BottomBar реализация
//============================================================================
@Composable
fun BottomNavigationBar(navController: NavController) {
    Box(Modifier.padding(bottom = 45.dp)) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colorScheme.background,//цвет фона
            modifier = Modifier.height(75.dp)
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Constants.BottomNavItems.forEach { navItem ->
                BottomNavigationItem(
                    selected = currentRoute == navItem.route,
                    onClick = {
                        navController.navigate(navItem.route) // обработка нажатия на объект
                    },
                    {
                        Image(
                            painter = painterResource(navItem.icon),
                            contentDescription = navItem.label,
                            modifier = Modifier.scale(
                                if (currentRoute ==
                                    navItem.route
                                ) 0.9F else 0.7F
                            )
                        ) // иконка объекта
                    },

                    label = {
                        Text(text = navItem.label)
                    },
                    alwaysShowLabel = false
                )
            } //подпись объекта
        }
    }
}

@Composable
fun TargetsScreen(){
    Column (modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box (Modifier.fillMaxWidth().height(150.dp).background(Color.White)){
            Row (modifier = Modifier.padding(top=50.dp, start = 30.dp)){
                Image(painter = painterResource(R.drawable.img),
                    contentDescription = "logo",
                    modifier = Modifier.size(70.dp)
                )
                Box (Modifier.padding(top=25.dp,start = 25.dp)){
                    Text( text = "The Road to adulthood",
                        color = Color(0xFFA47676),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp)
                }
                Column (modifier = Modifier.padding(top=25.dp, start=30.dp)) {
                    Box (Modifier.padding(start=5.dp)){
                        Image(
                            painter = painterResource(R.drawable.icon_menu),
                            contentDescription = "menu",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Box {
                        Text(text = "menu",
                            color = Color(0xFF79747E),
                            fontSize = 14.sp)
                    }
                }
            }
        }

        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
            Row {
                Image(
                    painter = painterResource(R.drawable.icon_wallet),
                    contentDescription = "wallet",
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Finance",
                    fontSize = 24.sp,
                    color = Color(0xFFA47676),
                    modifier = Modifier.padding(start=5.dp)
                    )
            }
        }

    }
}

@Composable
fun AnalyzeScreen(){
    Column (modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box (Modifier.fillMaxWidth().height(150.dp).background(Color.White)){
            Row (modifier = Modifier.padding(top=50.dp, start = 30.dp)){
                Image(painter = painterResource(R.drawable.img),
                    contentDescription = "logo",
                    modifier = Modifier.size(70.dp)
                )
                Box (Modifier.padding(top=25.dp,start = 25.dp)){
                    Text( text = "The Road to adulthood",
                        color = Color(0xFFA47676),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp)
                }
                Column (modifier = Modifier.padding(top=25.dp, start=30.dp)) {
                    Box (Modifier.padding(start=5.dp)){
                        Image(
                            painter = painterResource(R.drawable.icon_menu),
                            contentDescription = "menu",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Box {
                        Text(text = "menu",
                            color = Color(0xFF79747E),
                            fontSize = 14.sp)
                    }
                }
            }
        }


    }
}

@Composable
fun LimitsScreen(){
    Column (modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box (Modifier.fillMaxWidth().height(150.dp).background(Color.White)){
            Row (modifier = Modifier.padding(top=50.dp, start = 30.dp)){
                Image(painter = painterResource(R.drawable.img),
                    contentDescription = "logo",
                    modifier = Modifier.size(70.dp)
                )
                Box (Modifier.padding(top=25.dp,start = 25.dp)){
                    Text( text = "The Road to adulthood",
                        color = Color(0xFFA47676),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp)
                }
                Column (modifier = Modifier.padding(top=25.dp, start=30.dp)) {
                    Box (Modifier.padding(start=5.dp)){
                        Image(
                            painter = painterResource(R.drawable.icon_menu),
                            contentDescription = "menu",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Box {
                        Text(text = "menu",
                            color = Color(0xFF79747E),
                            fontSize = 14.sp)
                    }
                }
            }
        }


    }
}