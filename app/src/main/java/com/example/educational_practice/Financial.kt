package com.example.educational_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.educational_practice.ui.theme.EducationalpracticeTheme

class Financial : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TargetsScreen()
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
                /*Text()
                Column {
                    Image()
                    Text()
                }*/
            }
        }
    }
}