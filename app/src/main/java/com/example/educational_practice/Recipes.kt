package com.example.educational_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.educational_practice.ui.theme.EducationalpracticeTheme
import kotlinx.coroutines.launch

class Recipes : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            /*var navController = rememberNavController()
            Surface() {
                Scaffold (
                    topBar = { com.example.educational_practice.TopAppBar(navController =
                    navController)},
                    bottomBar = {
                        BottomNavigationBar(navController =
                        navController)
                    },
                    content = {
                            padding -> NavHostContainer(navController
                    = navController, padding = padding)
                    }
                )
            }*/
            RecipesScreen()
        }
    }
}


@Composable
fun RecipesScreen(){
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFFFFEFA))) {
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
                        //scope.launch { drawerState.open() }
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
        Box(Modifier.fillMaxWidth().wrapContentSize(Alignment.Center)) {
            Row {
                Image(
                    painter = painterResource(R.drawable.icon_food),
                    contentDescription = "wallet",
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

