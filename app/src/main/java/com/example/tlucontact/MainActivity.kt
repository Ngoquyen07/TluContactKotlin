package com.example.tlucontact

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tlucontact.ui.myui.MainScreen
import com.example.tlucontact.ui.theme.TluContactTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TluContactTheme {
                MainScreen()
            }
        }
    }
}

