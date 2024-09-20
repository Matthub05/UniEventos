package com.example.unieventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.unieventos.ui.screens.HomeScreen
import com.example.unieventos.ui.screens.LoginScreen
import com.example.unieventos.ui.theme.UniEventosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            UniEventosTheme {
                var isAuthenticated by remember { mutableStateOf(false) }

                if (isAuthenticated) {
                    HomeScreen()
                } else {
                    LoginScreen(onLoginSuccess = { isAuthenticated = true })
                }
            }

        }
    }
}