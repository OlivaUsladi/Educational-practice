package com.example.educational_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
//класс для хранения элементов накопления
//############################################################################
data class Targets(
    val title: String,
    val Target: Int,
    val Current: Int,
)

//############################################################################
//класс для хранения анализа бюджета
//############################################################################
data class Budget(
    val title: String,
    val Current: Int,
    val LastMonth: Int,
)

//############################################################################
//класс для хранения доходов
//############################################################################
data class Income(
    val Amount: Int,
    val Type: String,
    val description: String,
)

//############################################################################
//класс для хранения расходов
//############################################################################
data class Expense(
    val Amount: Int,
    val Type: String,
    val description: String,
)


sealed class RoutesFinance(val route: String) {
    object CreateIncome : Routes("income")
    object CreateExpense : Routes("expense")
    object pageOfIncome : Routes("pageofincome")
}

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
                AnalyzeScreen(navController)
            }
            composable("route 3") {
                LimitsScreen()
            }
            composable("income") {
                CreateIncome(navController)
            }
            composable("expense") {
                CreateExpense(navController)
            }
            composable("pageofincome"){
                pageOfIncome(navController)
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

val targetsList = listOf(Targets("Food", 1500, 1000), Targets("Transport", 200, 200),
    Targets("Coffee", 1000, 1500), Targets("Entertainments", 3000, 1200))

val cashList = mutableListOf(200, 500, 800, 250)
val sberbankList = mutableListOf(1000, 500, 9600, 1250)
val centerinvestList = mutableListOf(321, 562, 856)

val incomeList = mutableListOf(Income(200, "Cash", "Мама дала"), Income(500, "Cash", "Жора вернул"),
    Income(250, "Cash", "Сдача на рынке"),
    Income(1000, "Sberbank", "Оплатили урок Маша"), Income(500, "Sberbank", "Оплатили урок Света"),
    Income(1250, "Sberbank", "Нет описания"),
    Income(321, "Center-Invest", "Коля прислал"),  Income(9600, "Sberbank", "Папа прислал"),
    Income(562, "Center-Invest", "Миша за принтер"),
    Income(856, "Center-Invest", "Люда за обед"), Income(800, "Cash", "Оплатили урок Наталья"))



val cashListex = mutableListOf(20, 50, 800, 250)
val sberbankListex = mutableListOf(1000, 50, 900, 150)
val centerinvestListex = mutableListOf(21, 562, 856)

@Composable
fun TargetsScreen() {


    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box(Modifier.fillMaxWidth().height(150.dp).background(Color.White)) {
            Row(modifier = Modifier.padding(top = 50.dp, start = 30.dp)) {
                Image(painter = painterResource(R.drawable.img),
                    contentDescription = "logo",
                    modifier = Modifier.size(70.dp))
                Box(Modifier.padding(top = 25.dp, start = 25.dp)) {
                    Text(text = "The Road to adulthood",
                        color = Color(0xFFA47676),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp)
                }
                Column(modifier = Modifier.padding(top = 25.dp, start = 30.dp)) {
                    Box(Modifier.padding(start = 5.dp)) {
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
                    modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
            }
        }

        // LazyColumn для отображения списка
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем элементы
        ) {
            items(targetsList) { item ->
                // Каждый элемент списка
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFF3F3), shape = RoundedCornerShape(20.dp))
                        .width(300.dp)
                        .height(150.dp)
                        .align(Alignment.CenterHorizontally) // Центрируем Box внутри LazyColumn
                ) {
                    var More = item.Target-item.Current
                    Column (modifier = Modifier.fillMaxSize().padding(start=7.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)){
                        Box(Modifier.fillMaxWidth()) {
                            Text(
                                text = item.title,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                        Row {
                            Text(text = "Target: ",
                                fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(text = item.Target.toString(),
                                fontSize = 18.sp)
                        }
                        Row {
                            Text(text = "Current: ",
                                fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(7.dp))
                            Text(text = item.Current.toString(),
                                fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Required: ",
                                fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(7.dp))
                            if (More<0){
                                More=0
                            }
                            Text(text = More.toString(),
                                fontSize = 18.sp)
                        }
                        //Сделать эту хрень кликабельной
                        Box(Modifier.fillMaxWidth().padding(top=5.dp)) {
                            Text(
                                text = "Change",
                                fontSize = 20.sp,
                                color = Color(0xFFE68DF4),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun CreateIncome(navController: NavController){
    val description = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val Intamount = remember { mutableStateOf(0) }
    var errorFlag = remember{mutableStateOf(false)}
    val bank = remember { mutableStateOf("Cash") }
    val banks = listOf("Cash", "Sberbank", "Center-Invest")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(banks[0]) }


    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA)).padding(top=80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(modifier = Modifier.height(60.dp),
            value = description.value,
            onValueChange = { description.value = it },
            isError = errorFlag.value,
            placeholder = {
                Text(
                    text = "Enter description",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            })
        Spacer(modifier = Modifier.height(25.dp))
        TextField(modifier = Modifier.height(60.dp),
            value = amount.value,
            onValueChange = { amount.value = it },
            isError = errorFlag.value,
            placeholder = {
                Text(
                    text = "Enter amount",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            })
        Spacer(modifier = Modifier.height(25.dp))
        Column(Modifier.selectableGroup()) {
            banks.forEach { text ->
                Row( Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text)
                            bank.value = text
                        }
                    )
                    Text( text = text, fontSize = 24.sp )
                }
            }
        }
        Row (modifier = Modifier.padding(top=70.dp)){
            Button(onClick = {
                navController.navigate("route 2")
            }) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(onClick = {
                Intamount.value = amount.value.toInt()
                when (bank.value) {
                    "Cash" -> cashList.add(Intamount.value)
                    "Sberbank" -> sberbankList.add(Intamount.value)
                    "Center-Invest" -> centerinvestList.add(Intamount.value)
                }
                incomeList.add(Income(Intamount.value, bank.value, description.value))
                updateBudgetList()
                navController.navigate("route 2")
            }) {
                Text("Send")
            }
        }
    }
}



@Composable
fun CreateExpense(navController: NavController){
    val description = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val Intamount = remember { mutableStateOf(0) }
    var errorFlag = remember{mutableStateOf(false)}
    val bank = remember { mutableStateOf("Cash") }
    val banks = listOf("Cash", "Sberbank", "Center-Invest")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(banks[0]) }


    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA)).padding(top=80.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(modifier = Modifier.height(60.dp),
            value = description.value,
            onValueChange = { description.value = it },
            isError = errorFlag.value,
            placeholder = {
                Text(
                    text = "Enter description",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            })
        Spacer(modifier = Modifier.height(25.dp))
        TextField(modifier = Modifier.height(60.dp),
            value = amount.value,
            onValueChange = { amount.value = it },
            isError = errorFlag.value,
            placeholder = {
                Text(
                    text = "Enter amount",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            })
        Spacer(modifier = Modifier.height(25.dp))
        Column(Modifier.selectableGroup()) {
            banks.forEach { text ->
                Row( Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically)
                {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = { onOptionSelected(text)
                            bank.value = text
                        }
                    )
                    Text( text = text, fontSize = 24.sp )
                }
            }
        }
        Row (modifier = Modifier.padding(top=70.dp)){
            Button(onClick = {
                navController.navigate("route 2")
            }) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(30.dp))
            Button(onClick = {
                Intamount.value = amount.value.toInt()
                when (bank.value) {
                    "Cash" -> cashListex.add(Intamount.value)
                    "Sberbank" -> sberbankListex.add(Intamount.value)
                    "Center-Invest" -> centerinvestListex.add(Intamount.value)
                }
                updateBudgetList()
                navController.navigate("route 2")
            }) {
                Text("Send")
            }
        }
    }
}


@Composable
fun pageOfIncome(navController: NavController) {
    Box(
        Modifier.fillMaxSize().background(Color(0xFFFFFEFA))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().align(Alignment.Center),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем элементы
        ) {
            items(incomeList) { item ->
                // Каждый элемент списка
                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .width(300.dp)
                        .height(130.dp)
                    ,
                    contentAlignment = Alignment.Center // Центрируем содержимое Box внутри LazyColumn
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().padding(start = 7.dp),
                        verticalArrangement = Arrangement.Center, // Центрируем содержимое Column по вертикали
                        horizontalAlignment = Alignment.CenterHorizontally // Центрируем содержимое Column по горизонтали
                    ) {
                        Box(Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center) {
                            Text(
                                text = item.Type,
                                fontSize = 20.sp,
                                color = Color(0xFF6C4444),
                                textAlign = TextAlign.Center
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(7.dp))
                            Text(
                                text = item.Amount.toString(),
                                color = Color(0xFF6C4444),
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.width(7.dp))
                        Text(
                            text = item.description,
                            color = Color(0xFF6C4444),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}

val budgetList = mutableListOf(Budget("Income for cash", cashList.sum(), 2500), Budget("Income for Sberbank", sberbankList.sum(), 12000),
    Budget("Income for Center-Invest", centerinvestList.sum(), 3006), Budget("Expense for cash", cashListex.sum(), 1500),
    Budget("Expense for Sberbank", sberbankListex.sum(), 10954), Budget("Expense for Center-Invest", centerinvestListex.sum(), 2296))



fun updateBudgetList() {
    budgetList.clear() // Очистить текущий список
    budgetList.add(Budget("Income for cash", cashList.sum(), 2500))
    budgetList.add(Budget("Income for Sberbank", sberbankList.sum(), 12000))
    budgetList.add(Budget("Income for Center-Invest", centerinvestList.sum(), 3006))
    budgetList.add(Budget("Expense for cash", cashListex.sum(), 1500))
    budgetList.add(Budget("Expense for Sberbank", sberbankListex.sum(), 10954))
    budgetList.add(Budget("Expense for Center-Invest", centerinvestListex.sum(), 2296))
}


@Composable
fun AnalyzeScreen(navController: NavController){

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box(Modifier.fillMaxWidth().height(150.dp).background(Color.White)) {
            Row(modifier = Modifier.padding(top = 50.dp, start = 30.dp)) {
                Image(painter = painterResource(R.drawable.img),
                    contentDescription = "logo",
                    modifier = Modifier.size(70.dp))
                Box(Modifier.padding(top = 25.dp, start = 25.dp)) {
                    Text(text = "The Road to adulthood",
                        color = Color(0xFFA47676),
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 20.sp)
                }
                Column(modifier = Modifier.padding(top = 25.dp, start = 30.dp)) {
                    Box(Modifier.padding(start = 5.dp)) {
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
                    modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween // Распределяет пространство между элементами
        ) {
            Column(
                modifier = Modifier.weight(1f), // Задает вес первой колонки
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(Modifier.padding(start = 15.dp).wrapContentWidth(Alignment.CenterHorizontally)) {
                    Column {
                        Box(Modifier.clickable(){
                            navController.navigate(RoutesFinance.CreateIncome.route)
                        }) {
                            Column {
                                Image(
                                    painter = painterResource(R.drawable.icon_add),
                                    contentDescription = "add_icon",
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = "Add income",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Box(Modifier.clickable(){
                            navController.navigate(RoutesFinance.pageOfIncome.route)
                        }) {
                            Column {
                                Image(
                                    painter = painterResource(R.drawable.icon_open),
                                    contentDescription = "open_icon",
                                    modifier = Modifier.size(24.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "My income",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.wrapContentWidth(Alignment.End),
                horizontalAlignment = Alignment.End
            ) {
                Box(Modifier.padding(end = 15.dp).wrapContentWidth(Alignment.CenterHorizontally)) {
                    Column {
                        Box(Modifier.clickable(){
                            navController.navigate(RoutesFinance.CreateExpense.route)
                        }) {
                            Column {
                                Image(
                                    painter = painterResource(R.drawable.icon_add),
                                    contentDescription = "add_icon",
                                    modifier = Modifier.size(24.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                                Text(
                                    text = "Add expense",
                                    fontSize = 16.sp,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(15.dp))
                        Image(
                            painter = painterResource(R.drawable.icon_open),
                            contentDescription = "open_icon",
                            modifier = Modifier.size(24.dp).align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = "My expense",
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)){
            Text(text = "Report (December)",
               fontSize = 20.sp,
                color = Color(0xFF735454),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom=30.dp))
        }



        // LazyColumn для отображения списка
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally // Центрируем элементы
        ) {
            items(budgetList) { item ->
                // Каждый элемент списка
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFA2A2), shape = RoundedCornerShape(20.dp))
                        .width(300.dp)
                        .height(130.dp)
                        .align(Alignment.CenterHorizontally) // Центрируем Box внутри LazyColumn
                ) {
                    Column (modifier = Modifier.fillMaxSize().padding(start=7.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)){
                        Box(Modifier.fillMaxWidth()) {
                            Text(
                                text = item.title,
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                        Column {
                            Row {
                                Text(
                                    text = "Current: ",
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = item.Current.toString(),
                                    fontSize = 18.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Row {
                                Text(
                                    text = "Last month: ",
                                    fontSize = 18.sp
                                )
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = item.LastMonth.toString(),
                                    fontSize = 18.sp
                                )
                            }
                        }

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