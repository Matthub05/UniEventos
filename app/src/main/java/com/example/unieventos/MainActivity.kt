package com.example.unieventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.unieventos.ui.navigation.Navigation
import com.example.unieventos.ui.theme.UniEventosTheme
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.UsersViewModel

class MainActivity : ComponentActivity() {

    private val eventsviewModel: EventsViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()
    private val artistViewModel: ArtistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            UniEventosTheme {
                Navigation(
                    eventsViewModel = eventsviewModel,
                    usersViewModel = usersViewModel,
                    artistViewModel = artistViewModel
                )
            }

        }
    }
}