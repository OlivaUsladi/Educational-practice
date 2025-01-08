package com.example.educational_practice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.educational_practice.ui.theme.EducationalpracticeTheme
import kotlinx.coroutines.launch
import java.util.Locale

class Recipes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Surface() {
                Scaffold (
                    topBar = { TopAppBarRecipe(navController =
                    navController)
                    },
                    content = {
                            padding -> NavHostContainerRecipe(navController
                    = navController, padding = padding)
                    }
                )
            }

        }
    }
}


@Composable
fun NavHostContainerRecipe(
    navController: NavHostController, //передаем NavHostController
    padding: PaddingValues //передаем отступ
) {
    NavHost(navController = navController, startDestination = RoutesRecipes.RecipesScreen.route,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(RoutesRecipes.RecipesScreen.route) {
                RecipesScreen(navController)
            }
            composable(RoutesRecipes.RecipeScreen.route + "/{index}") { stackEntry ->
                val index = stackEntry.arguments?.getString("index")
                RecipeScreen(navController, index.toString())
            }
        }
    )
}


sealed class RoutesRecipes(val route: String) {
    object RecipesScreen : Routes("RecipesScreen")
    object RecipeScreen : Routes("RecipeScreen")
}

//############################################################################
//класс для хранения шагов
//############################################################################
data class Step(
    val Number: Int,
    val description: String,
)


//############################################################################
//класс для хранения рецептов
//############################################################################
data class Recipe(
    val title: String,
    val description: String,
    val steps: List<Step>,
    val time: Int,
    val cuisine: String,
    val ingredients: List<String>
)
// Шаги для рецепта "Борщ"
val borschtSteps = listOf(
    Step(1, "Варите свеклу в кожуре до мягкости."),
    Step(2, "Остудите свеклу и натрите на терке."),
    Step(3, "В кастрюле обжарьте лук и морковь."),
    Step(4, "Добавьте капусту и свеклу, тушите 5 минут."),
    Step(5, "Добавьте мясной бульон и варите 30 минут."),
    Step(6, "Посолите и поперчите по вкусу. Подавайте со сметаной.")
)

// Рецепт "Борщ"
val borschtRecipe = Recipe(
    title = "Борщ",
    description = "Традиционный русский суп.",
    steps = borschtSteps,
    time = 60,
    cuisine = "Русская",
    ingredients = listOf("Свекла", "Капуста", "Морковь", "Лук", "Картофель", "Мясной бульон", "Сметана", "Соль", "Перец")
)

// Шаги для рецепта "Пирог с яблоками"
val applePieSteps = listOf(
    Step(1, "Приготовьте тесто, смешав муку, сахар и масло."),
    Step(2, "Раскатайте тесто и выложите его в форму."),
    Step(3, "Нарежьте яблоки и выложите на тесто."),
    Step(4, "Смешайте сахар и корицу, посыпьте яблоки."),
    Step(5, "Закройте пирог вторым слоем теста и сделайте отверстия для выхода пара."),
    Step(6, "Выпекайте в предварительно разогретой до 180°C духовке 30-40 минут.")
)

// Рецепт "Пирог с яблоками"
val applePieRecipe = Recipe(
    title = "Пирог с яблоками",
    description = "Вкусный десерт с яблоками.",
    steps = applePieSteps,
    time = 50,
    cuisine = "Русская",
    ingredients = listOf("Мука", "Сахар", "Масло", "Яблоки", "Корица", "Яйцо")
)
// Шаги для рецепта "Паста Карбонара"
val carbonaraSteps = listOf(
    Step(1, "Отварите пасту до состояния аль денте."),
    Step(2, "В миске взбейте яйца, добавьте тертый сыр пармезан и перец."),
    Step(3, "Обжарьте бекон до хрустящей корочки."),
    Step(4, "Слейте воду с пасты и добавьте её к бекону."),
    Step(5, "Снимите сковороду с огня и добавьте яичную смесь, быстро перемешивая."),
    Step(6, "Подавайте сразу, посыпав тертым пармезаном.")
)

// Рецепт "Паста Карбонара"
val carbonaraRecipe = Recipe(
    title = "Паста Карбонара",
    description = "Классическое итальянское блюдо.",
    steps = carbonaraSteps,
    time = 20,
    cuisine = "Итальянская",
    ingredients = listOf("Спагетти", "Яйца", "Бекон", "Пармезан", "Черный перец")
)

// Шаги для рецепта "Салат Цезарь"
val caesarSaladSteps = listOf(
    Step(1, "Нарежьте хлеб кубиками и поджарьте на сковороде или в духовке до хрустящей корочки."),
    Step(2, "Нарежьте листья салата романо."),
    Step(3, "Приготовьте соус, смешав майонез, горчицу, чеснок, лимонный сок и тертый пармезан."),
    Step(4, "Выложите салат на тарелку, добавьте сухарики и заправьте соусом."),
    Step(5, "Сверху посыпьте тертым пармезаном и добавьте нарезанное отварное яйцо (по желанию)."),
    Step(6, "Подавайте сразу.")
)

// Рецепт "Салат Цезарь"
val caesarSaladRecipe = Recipe(
    title = "Салат Цезарь",
    description = "Популярный салат с хрустящими сухариками.",
    steps = caesarSaladSteps,
    time = 15,
    cuisine = "Американская",
    ingredients = listOf("Салат Романо", "Хлеб", "Майонез", "Горчица", "Чеснок", "Лимонный сок", "Пармезан", "Яйцо (опционально)")
)

// Шаги для рецепта "Курица с овощами в духовке"
val bakedChickenSteps = listOf(
    Step(1, "Нарежьте овощи крупными кусками."),
    Step(2, "Смешайте овощи и курицу с оливковым маслом, солью, перцем и травами."),
    Step(3, "Выложите все в форму для запекания."),
    Step(4, "Запекайте в предварительно разогретой до 200°C духовке 40-50 минут, до готовности курицы.")
)

// Рецепт "Курица с овощами в духовке"
val bakedChickenRecipe = Recipe(
    title = "Курица с овощами в духовке",
    description = "Простое и сытное блюдо.",
    steps = bakedChickenSteps,
    time = 50,
    cuisine = "Европейская",
    ingredients = listOf("Куриные бедра или голени", "Картофель", "Морковь", "Лук", "Перец", "Оливковое масло", "Соль", "Перец", "Травы")
)
// Шаги для рецепта "Омлет с сыром"
val omeletteSteps = listOf(
    Step(1, "Взбейте яйца с солью и перцем."),
    Step(2, "Разогрейте сковороду с небольшим количеством масла."),
    Step(3, "Вылейте яичную смесь на сковороду."),
    Step(4, "Жарьте на среднем огне, пока омлет не схватится по краям."),
    Step(5, "Посыпьте омлет тертым сыром."),
    Step(6, "Сложите омлет пополам или оставьте открытым и подавайте.")
)

// Рецепт "Омлет с сыром"
val omeletteRecipe = Recipe(
    title = "Омлет с сыром",
    description = "Быстрый и простой завтрак.",
    steps = omeletteSteps,
    time = 10,
    cuisine = "Европейская",
    ingredients = listOf("Яйца", "Сыр", "Соль", "Перец", "Масло")
)
// Шаги для рецепта "Греческий салат"
val greekSaladSteps = listOf(
    Step(1, "Нарежьте огурцы, помидоры, перец и красный лук кубиками."),
    Step(2, "Нарежьте сыр фета кубиками."),
    Step(3, "Смешайте овощи, добавьте маслины и сыр."),
    Step(4, "Заправьте оливковым маслом и орегано."),
    Step(5, "Посолите и поперчите по вкусу."),
    Step(6, "Подавайте сразу или дайте немного настояться.")
)

// Рецепт "Греческий салат"
val greekSaladRecipe = Recipe(
    title = "Греческий салат",
    description = "Легкий и освежающий салат.",
    steps = greekSaladSteps,
    time = 15,
    cuisine = "Греческая",
    ingredients = listOf("Огурцы", "Помидоры", "Перец", "Красный лук", "Сыр фета", "Маслины", "Оливковое масло", "Орегано", "Соль", "Перец")
)
//Список рецептов
val recipes = listOf(borschtRecipe, applePieRecipe, carbonaraRecipe, caesarSaladRecipe, bakedChickenRecipe, omeletteRecipe, greekSaladRecipe)


//============================================================================
//TopAppBar реализация
//============================================================================
@Composable
fun TopAppBarRecipe(navController: NavController) {
    val items = listOf("Finance", "Recipes", "Tips")
    val selectedItem = remember { mutableStateOf(items[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val intentfin = Intent(context, Financial::class.java)
    val intenttips = Intent(context, Tips::class.java)



    Box(Modifier.fillMaxWidth().height(if (drawerState.isOpen) 250.dp else 150.dp)) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet{
                    items.forEach { item ->
                        TextButton(
                            onClick = {
                                scope.launch { drawerState.close() }
                                selectedItem.value = item
                                when (selectedItem.value){
                                    "Finance" -> context.startActivity(intentfin)
                                    "Recipes" -> navController.navigate(RoutesRecipes.RecipesScreen.route)
                                    "Tips" -> context.startActivity(intenttips)
                                }
                            },
                        ) { Text(item, fontSize = 22.sp) }
                    }
                }
            },
            content={
                Box(Modifier.fillMaxWidth().height(150.dp).background(Color.White)) {
                    Row(modifier = Modifier.padding(top = 50.dp, start = 30.dp)) {
                        Image(
                            painter = painterResource(R.drawable.img),
                            contentDescription = "logo",
                            modifier = Modifier.size(70.dp)
                        )
                        Box(Modifier.padding(top = 25.dp, start = 25.dp)) {
                            Text(
                                text = "The Road to adulthood",
                                color = Color(0xFFA47676),
                                modifier = Modifier.align(Alignment.Center),
                                fontSize = 20.sp
                            )
                        }
                        Column(modifier = Modifier.padding(top = 25.dp, start = 30.dp)) {
                            Box(Modifier.padding(start = 5.dp).clickable() {
                                scope.launch { drawerState.open() }
                            }) {
                                Image(
                                    painter = painterResource(R.drawable.icon_menu),
                                    contentDescription = "menu",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Box {
                                Text(
                                    text = "menu",
                                    color = Color(0xFF79747E),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        )
    }

}


@Composable
fun RecipesScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {

        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
            Row {
                Image(
                    painter = painterResource(R.drawable.icon_food),
                    contentDescription = "food",
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Recipes",
                    fontSize = 24.sp,
                    color = Color(0xFFA47676),
                    modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
            }
        }
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)
        Spacer(modifier = Modifier.height(10.dp))
        RecipesList(navController = navController, state = textState)
    }
}


@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape =  RoundedCornerShape(16.dp)),
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
            //Image(painter = painterResource(R.drawable.icon_sort), contentDescription = "")
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape =  RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            cursorColor = Color.Black,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            backgroundColor = Color(0xFFFFF7F7),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}


@Composable
fun RecipesList(navController: NavController, state: MutableState<TextFieldValue>) {
    var filteredRecipes: List<Recipe> = emptyList()
    LazyColumn( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally ) {
        val searchedText = state.value.text
        filteredRecipes = if (searchedText.isEmpty()) {
            recipes // Если текст поиска пуст, вернуть все рецепты
        } else {
            val resultList =mutableListOf<Recipe>()
            for (recipe in recipes) {
                if (recipe.title.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) ||
                    recipe.cuisine.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) ||
                    recipe.description.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) ||
                    recipe.ingredients.any { it.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) } ||
                    recipe.time.toString().contains(searchedText)) {

                    resultList.add(recipe)
                }

            }
            resultList
        }
        itemsIndexed(filteredRecipes) { index,recipe ->
            Box(Modifier.width(350.dp).height(150.dp). background(Color(0xFFFFF7F7), shape = RoundedCornerShape(10.dp))){
                Column (modifier = Modifier.fillMaxSize().padding(start=7.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)){
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = recipe.title,
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                    Row {
                        Text(text = "Время приготовления: ",
                            fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(7.dp))
                        Text(text = recipe.time.toString(),
                            fontSize = 18.sp)
                    }
                    Row {
                        Text(text = "Кухня: ",
                            fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(text = recipe.cuisine,
                            fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(5.dp))

                    }
                    Box(Modifier.fillMaxWidth().padding(top=5.dp).clickable(){
                        if (filteredRecipes != recipes) {
                            val i = recipes.indexOfFirst { it.title.equals(recipe.title, ignoreCase = true) }
                            if (i!=-1){
                                navController.navigate(RoutesRecipes.RecipeScreen.route + "/${i.toString()}")
                            }
                        }
                        else {
                            navController.navigate(RoutesRecipes.RecipeScreen.route + "/${index.toString()}")
                        }
                    }) {
                        Text(
                            text = "More",
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

@Composable
fun RecipeScreen(navController: NavController, index: String){
    val i = index.toInt()
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
                Text(text = recipes[i].title,
                    fontSize = 24.sp,
                    color = Color(0xFFA47676),
                    modifier = Modifier.padding(start = 5.dp, bottom = 30.dp, top = 25.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
        LazyColumn {
            item {
                Box(Modifier.fillMaxWidth()) {
                    Text(text = "Описание: " + recipes[i].description,
                        fontSize = 24.sp,
                        color = Color(0xFFA47676),
                        modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
                }
            }
            item {
                Box(Modifier.fillMaxWidth()) {
                    Text(text = "Время приготовления (в минутах): " + recipes[i].time,
                        fontSize = 24.sp,
                        color = Color(0xFFA47676),
                        modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
                }
            }
            item {
                Box(Modifier.fillMaxWidth()) {
                    Text(text = "Ингредиенты:",
                        fontSize = 24.sp,
                        color = Color(0xFFA47676),
                        modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
                }
            }
            val ingr = recipes[i].ingredients
            items(ingr){ item ->
                Box(Modifier.fillMaxWidth()) {
                    Text(text = item,
                        fontSize = 24.sp,
                        color = Color(0xFF786262),
                        modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
                }
            }
            item {
                Box(Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            text = "Кухня: " + recipes[i].cuisine,
                            fontSize = 24.sp,
                            color = Color(0xFFA47676),
                            modifier = Modifier.padding(start = 5.dp, bottom = 30.dp)
                        )
                        Text(
                            text = "Шаги приготовления: ",
                            fontSize = 24.sp,
                            color = Color(0xFFB1A5B8),
                            modifier = Modifier.padding(start = 5.dp, bottom = 30.dp)
                        )
                    }
                }
            }
            val listSteps = recipes[i].steps
            items(listSteps){ item ->
                Box(Modifier.fillMaxWidth()) {
                    Text(text = "Шаг " + item.Number.toString() + " " + item.description,
                        fontSize = 24.sp,
                        color = Color(0xFF786262),
                        modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
                }
            }
            item{
                Spacer(modifier = Modifier.height(15.dp))
            }

        }
    }
}