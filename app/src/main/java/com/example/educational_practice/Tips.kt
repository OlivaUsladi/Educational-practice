package com.example.educational_practice

import android.content.Intent
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import java.util.Locale

class Tips : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            Surface() {
                Scaffold (
                    topBar = { TopAppBarTips(navController =
                    navController)
                    },
                    content = {
                            padding -> NavHostContainerTips(navController
                    = navController, padding = padding)
                    }
                )
            }

        }
    }
}

@Composable
fun NavHostContainerTips(
    navController: NavHostController, //передаем NavHostController
    padding: PaddingValues //передаем отступ
) {
    NavHost(navController = navController, startDestination = RoutesTips.TipsScreen.route,
        modifier = Modifier.padding(paddingValues = padding),
        builder = {
            composable(RoutesTips.TipsScreen.route) {
                TipsScreen(navController)
            }
            composable(RoutesTips.TipScreen.route + "/{index}") { stackEntry ->
                val index = stackEntry.arguments?.getString("index")
                TipScreen(navController, index.toString())
            }
        }
    )
}

sealed class RoutesTips(val route: String) {
    object TipsScreen : Routes("TipsScreen")
    object TipScreen : Routes("TipScreen")
}

//============================================================================
//TopAppBar реализация
//============================================================================
@Composable
fun TopAppBarTips(navController: NavController) {
    val items = listOf("Finance", "Recipes", "Tips")
    val selectedItem = remember { mutableStateOf(items[0]) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val intentfin = Intent(context, Financial::class.java)
    val intentrec = Intent(context, Recipes::class.java)



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
                                    "Recipes" -> context.startActivity(intentrec)
                                    "Tips" -> navController.navigate(RoutesTips.TipsScreen.route)
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


//############################################################################
//класс для хранения шагов
//############################################################################
data class StepTips(
    val Number: Int,
    val description: String,
)


//############################################################################
//класс для хранения рецептов
//############################################################################
data class Tip(
    val title: String,
    val description: String,
    val steps: List<StepTips>
)

// Пример списка, содержащего объекты типа Tip
val tipsList: List<Tip> = listOf(
    Tip(
        title = "Как сделать идеальный омлет",
        description = "Следуйте этим шагам, чтобы приготовить идеальный омлет.",
        steps = listOf(
            StepTips(Number = 1, description = "Взбейте яйца в миске с щепоткой соли."),
            StepTips(Number = 2, description = "Разогрейте сковороду на среднем огне и добавьте масло."),
            StepTips(Number = 3, description = "Вылейте яйца на сковороду и ждите, пока края начнут схватываться."),
            StepTips(Number = 4, description = "Используя лопатку, аккуратно поднимите края омлета."),
            StepTips(Number = 5, description = "Когда омлет готов, сложите его пополам и подавайте на стол.")
        )
    ),
    Tip(
        title = "Как давать и принимать обратную связь",
        description = "Советы по тому, как эффективно давать и принимать обратную связь.",
        steps = listOf(
            StepTips(Number = 1, description = "При даче обратной связи будьте конкретны и описывайте конкретные ситуации."),
            StepTips(Number = 2, description = "Фокусируйтесь на поведении, а не на личности."),
            StepTips(Number = 3, description = "Начинайте с позитивных моментов, а затем переходите к критике."),
            StepTips(Number = 4, description = "Предлагайте конкретные способы улучшения."),
            StepTips(Number = 5, description = "Принимая обратную связь, слушайте внимательно и не перебивайте."),
            StepTips(Number = 6, description = "Поблагодарите за обратную связь и попробуйте извлечь из нее пользу."),
            StepTips(Number = 7, description = "Не воспринимайте критику как личное оскорбление.")

        )
    ),
    Tip(
        title = "Приготовление риса",
        description = "Узнайте, как правильно приготовить рис.",
        steps = listOf(
            StepTips(Number = 1, description = "Тщательно промойте рис под холодной водой."),
            StepTips(Number = 2, description = "Добавьте рис в кастрюлю с водой в соотношении 1:2."),
            StepTips(Number = 3, description = "Доведите до кипения, затем уменьшите огонь и накройте крышкой."),
            StepTips(Number = 4, description = "Готовьте 15-20 минут, пока вся вода не впитается."),
            StepTips(Number = 5, description = "Снимите с огня и дайте постоять под крышкой еще 5 минут.")
        )
    ),
    Tip(
        title = "Как испечь вкусный хлеб",
        description = "Следуйте этим шагам для выпечки домашнего хлеба.",
        steps = listOf(
            StepTips(Number = 1, description = "Смешайте муку, дрожжи, соль и воду в большой миске."),
            StepTips(Number = 2, description = "Замесите тесто до однородной массы."),
            StepTips(Number = 3, description = "Накройте тесто и оставьте его в теплое место на 1-2 часа."),
            StepTips(Number = 4, description = "Сформируйте хлеб и оставьте его подниматься еще на 30 минут."),
            StepTips(Number = 5, description = "Выпекайте при температуре 220°C в течение 30-35 минут.")
        )
    ),
    Tip(
        title = "Приготовление пасты",
        description = "Научитесь готовить идеальную пасту.",
        steps = listOf(
            StepTips(Number = 1, description = "Доведите большую кастрюлю воды до кипения."),
            StepTips(Number = 2, description = "Добавьте соль и пасту в кипящую воду."),
            StepTips(Number = 3, description = "Готовьте пасту до состояния Al Dente, следуя инструкции на упаковке."),
            StepTips(Number = 4, description = "Слейте воду и промойте пасту под холодной водой."),
            StepTips(Number = 5, description = "Подавайте с вашим любимым соусом.")
        )
    ),
    Tip(
        title = "Как приготовить салат Цезарь",
        description = "Рецепт классического салата Цезарь.",
        steps = listOf(
            StepTips(Number = 1, description = "Промойте и нарежьте ромэйн-салат."),
            StepTips(Number = 2, description = "Приготовьте соус, смешав майонез, чеснок, лимонный сок и анчоусы."),
            StepTips(Number = 3, description = "Смешайте салат с соусом и добавьте тертый пармезан."),
            StepTips(Number = 4, description = "Добавьте сухарики и аккуратно перемешайте."),
            StepTips(Number = 5, description = "Подавайте сразу же на стол.")
        )
    ),
    Tip(
        title = "Как организовать гардероб",
        description = "Советы по эффективной организации вашего гардероба.",
        steps = listOf(
            StepTips(Number = 1, description = "Разделите всю одежду на категории: верхняя одежда, брюки, рубашки и т.д."),
            StepTips(Number = 2, description = "Уберите всю одежду, которую вы не носили в течение года."),
            StepTips(Number = 3, description = "Используйте вешалки одного типа для создания визуального порядка."),
            StepTips(Number = 4, description = "Складывайте или вешайте одежду по цветам для легкого поиска."),
            StepTips(Number = 5, description = "Используйте органайзеры для хранения мелких вещей, таких как носки и белье.")
        )
    ),
    Tip(
        title = "Как выражать свою точку зрения",
        description = "Советы по тому, как четко и уверенно выражать свое мнение.",
        steps = listOf(
            StepTips(Number = 1, description = "Сформулируйте свою мысль четко и лаконично."),
            StepTips(Number = 2, description = "Используйте «Я-сообщения», чтобы выразить свои чувства и мысли, не обвиняя других."),
            StepTips(Number = 3, description = "Подкрепляйте свою точку зрения аргументами и фактами."),
            StepTips(Number = 4, description = "Говорите уверенно и спокойно, не повышая тон."),
            StepTips(Number = 5, description = "Будьте готовы выслушать другие точки зрения и вести диалог.")
        )
    ),
    Tip(
        title = "Как эффективно стирать белье",
        description = "Советы по правильной стирке белья.",
        steps = listOf(
            StepTips(Number = 1, description = "Разделите белье по цветам и типу ткани перед стиркой."),
            StepTips(Number = 2, description = "Используйте подходящий стиральный порошок или гель для каждого типа белья."),
            StepTips(Number = 3, description = "Устанавливайте правильный режим стирки и температуру."),
            StepTips(Number = 4, description = "Не перегружайте стиральную машину, чтобы обеспечить качественную стирку."),
            StepTips(Number = 5, description = "Сразу после стирки вынимайте белье из машины, чтобы избежать появления запаха.")
        )
    ),
    Tip(
        title = "Как эффективно планировать покупки продуктов",
        description = "Советы по созданию эффективного списка покупок.",
        steps = listOf(
            StepTips(Number = 1, description = "Составьте список продуктов перед походом в магазин."),
            StepTips(Number = 2, description = "Планируйте покупки на основе меню на неделю, чтобы избежать лишних затрат."),
            StepTips(Number = 3, description = "Проверьте наличие продуктов в холодильнике и шкафах, чтобы не купить лишнего."),
            StepTips(Number = 4, description = "Придерживайтесь списка покупок в магазине."),
            StepTips(Number = 5, description = "Не ходите в магазин на голодный желудок, чтобы избежать импульсивных покупок.")
        )
    ),
    Tip(
        title = "Как проветривать помещение",
        description = "Советы по правильному проветриванию помещений.",
        steps = listOf(
            StepTips(Number = 1, description = "Открывайте окна на 10-15 минут несколько раз в день."),
            StepTips(Number = 2, description = "Проветривайте помещение после уборки или готовки."),
            StepTips(Number = 3, description = "Проветривайте помещение даже в холодное время года."),
            StepTips(Number = 4, description = "Не оставляйте окна открытыми на долгое время, чтобы избежать переохлаждения."),
            StepTips(Number = 5, description = "Используйте приточные клапаны для постоянной циркуляции воздуха.")
        )
    ),
    Tip(
        title = "Приготовление смузи",
        description = "Как быстро сделать полезный смузи.",
        steps = listOf(
            StepTips(Number = 1, description = "Положите в блендер фрукты, например, банан и ягоди."),
            StepTips(Number = 2, description = "Добавьте йогурт или молоко для кремовой текстуры."),
            StepTips(Number = 3, description = "Долейте немного сока или воды для нужной консистенции."),
            StepTips(Number = 4, description = "Измельчите до однородной массы."),
            StepTips(Number = 5, description = "Разлейте по стаканам и наслаждайтесь!")
        )
    ),
    Tip(
        title = "Приготовление картофельного пюре",
        description = "Как сделать нежное картофельное пюре.",
        steps = listOf(
            StepTips(Number = 1, description = "Очистите и нарежьте картофель на куски."),
            StepTips(Number = 2, description = "Отварите картофель в подсоленной воде до мягкости."),
            StepTips(Number = 3, description = "Слейте воду и разомните картофель до однородной массы."),
            StepTips(Number = 4, description = "Добавьте молоко и масло, хорошо перемешайте."),
            StepTips(Number = 5, description = "Приправьте солью и перцем по вкусу.")
        )
    ),
    Tip(
        title = "Как эффективно организовать уборку",
        description = "Следуйте этим шагам, чтобы сделать уборку более эффективной и быстрой.",
        steps = listOf(
            StepTips(Number = 1, description = "Создайте список задач, которые нужно выполнить во время уборки."),
            StepTips(Number = 2, description = "Разделите пространство на зоны: кухня, ванная, гостиная и т.д."),
            StepTips(Number = 3, description = "Начинайте уборку с одной зоны и двигайтесь по часовой стрелке или против часовой стрелки."),
            StepTips(Number = 4, description = "Используйте специальные корзины для сбора мусора и вещей, которые не на месте."),
            StepTips(Number = 5, description = "На протяжении уборки включите приятную музыку, чтобы сделать процесс более веселым.")
        )
    ),
    Tip(
        title = "Как поддерживать чистоту на кухне",
        description = "Советы по поддержанию чистоты на кухне в повседневной жизни.",
        steps = listOf(
            StepTips(Number = 1, description = "Мойте посуду сразу после еды, чтобы избежать накопления грязной посуды."),
            StepTips(Number = 2, description = "Протирайте поверхности после каждого использования."),
            StepTips(Number = 3, description = "Регулярно выносите мусор и очищайте мусорное ведро."),
            StepTips(Number = 4, description = "Используйте контейнеры для хранения продуктов, чтобы избежать беспорядка."),
            StepTips(Number = 5, description = "Регулярно проводите глубокую очистку плиты и духовки.")
        )
    ),
    Tip(
        title = "Как слушать активно",
        description = "Советы по развитию навыков активного слушания.",
        steps = listOf(
            StepTips(Number = 1, description = "Сфокусируйтесь на говорящем: установите зрительный контакт, отложите свои дела."),
            StepTips(Number = 2, description = "Не перебивайте: дайте собеседнику возможность полностью высказаться."),
            StepTips(Number = 3, description = "Задавайте уточняющие вопросы, чтобы показать свой интерес и понимание."),
            StepTips(Number = 4, description = "Показывайте эмпатию: попробуйте понять чувства и эмоции собеседника."),
            StepTips(Number = 5, description = "Перефразируйте услышанное, чтобы убедиться в правильном понимании.")
            ,StepTips(Number = 6, description = "Подведите итог разговора, чтобы закрепить основные моменты.")
        )
    ),
    Tip(
        title = "Как справляться с конфликтами",
        description = "Советы по эффективному разрешению конфликтов.",
        steps = listOf(
            StepTips(Number = 1, description = "Сохраняйте спокойствие и не позволяйте эмоциям взять верх."),
            StepTips(Number = 2, description = "Выслушайте обе стороны конфликта, не перебивая."),
            StepTips(Number = 3, description = "Определите суть конфликта и его причины."),
            StepTips(Number = 4, description = "Ищите компромиссное решение, которое удовлетворит обе стороны."),
            StepTips(Number = 5, description = "Сосредоточьтесь на поиске решения, а не на обвинениях."),
            StepTips(Number = 6, description = "Если необходимо, обратитесь за помощью к посреднику.")
        )
    ),
    Tip(
        title = "Как поддерживать разговор",
        description = "Советы по тому, как сделать разговор интересным и вовлекающим.",
        steps = listOf(
            StepTips(Number = 1, description = "Задавайте открытые вопросы, на которые нельзя ответить односложно."),
            StepTips(Number = 2, description = "Проявляйте интерес к тому, что говорит собеседник."),
            StepTips(Number = 3, description = "Делитесь своими мыслями и чувствами, когда это уместно."),
            StepTips(Number = 4, description = "Ищите общие темы и интересы."),
            StepTips(Number = 5, description = "Помните о невербальной коммуникации: улыбка, зрительный контакт.")
            ,StepTips(Number = 6, description = "Не монополизируйте разговор, дайте возможность собеседнику тоже высказаться.")

        )
    )
)


@Composable
fun TipsScreen(navController: NavController){
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {

        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
            Row {
                Image(
                    painter = painterResource(R.drawable.icon_tips),
                    contentDescription = "tips",
                    modifier = Modifier.size(24.dp)
                )
                Text(text = "Tips",
                    fontSize = 24.sp,
                    color = Color(0xFFA47676),
                    modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
            }
        }
        val textState = remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState)
        Spacer(modifier = Modifier.height(10.dp))
        TipsList(navController = navController, state = textState)
    }
}

@Composable
fun TipsList(navController: NavController, state: MutableState<TextFieldValue>) {
    var filteredTips: List<Tip> = emptyList()
    LazyColumn( modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally ) {
        val searchedText = state.value.text
        filteredTips = if (searchedText.isEmpty()) {
            tipsList // Если текст поиска пуст, вернуть все рецепты
        } else {
            val resultList =mutableListOf<Tip>()
            for (tip in tipsList) {
                if (tip.title.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) ||
                    tip.description.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) ||
                    tip.steps.any { it.description.lowercase(Locale.getDefault()).contains(searchedText.lowercase(Locale.getDefault())) }) {

                    resultList.add(tip)
                }

            }
            resultList
        }
        itemsIndexed(filteredTips) { index,tip ->
            Box(Modifier.width(350.dp).height(160.dp). background(Color(0xFFFFF7F7), shape = RoundedCornerShape(10.dp))){
                Column (modifier = Modifier.fillMaxSize().padding(start=7.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)){
                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            text = tip.title,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            color = Color(0xFFA47676),
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                    LazyColumn {item {
                        Box(Modifier.fillMaxWidth()) {
                            Text(
                                text = "Шаг 1:" + tip.steps[0].description,
                                fontSize = 20.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.align(Alignment.TopCenter)
                            )
                        }
                    }
                        item {
                            Box(Modifier.fillMaxWidth().padding(top = 5.dp).clickable() {
                                if (filteredTips != tipsList) {
                                    val i = tipsList.indexOfFirst { it.title.equals(tip.title, ignoreCase = true) }
                                    if (i!=-1){
                                        navController.navigate(RoutesTips.TipScreen.route + "/${i.toString()}")
                                    }
                                }
                                else {
                                    navController.navigate(RoutesTips.TipScreen.route + "/${index.toString()}")
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
    }
}

@Composable
fun TipScreen(navController: NavController, index: String){
    val i = index.toInt()
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
            Text(text = tipsList[i].title,
                fontSize = 24.sp,
                color = Color(0xFFA47676),
                modifier = Modifier.padding(start = 5.dp, bottom = 30.dp, top = 25.dp))
        }
        Spacer(modifier = Modifier.height(25.dp))
        LazyColumn {
            item {
                Box(Modifier.fillMaxWidth()) {
                    Text(text = "Описание: " + tipsList[i].description,
                        fontSize = 24.sp,
                        color = Color(0xFFA47676),
                        modifier = Modifier.padding(start = 5.dp, bottom = 30.dp))
                }
            }

            item {
                Box(Modifier.fillMaxWidth()) {
                    Column {
                        Text(
                            text = "Шаги приготовления: ",
                            fontSize = 24.sp,
                            color = Color(0xFFB1A5B8),
                            modifier = Modifier.padding(start = 5.dp, bottom = 30.dp)
                        )
                    }
                }
            }
            val listSteps = tipsList[i].steps
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


