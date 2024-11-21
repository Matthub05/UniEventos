package com.example.unieventos.ui.screens.client.tabs

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.unieventos.R
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.Ticket
import com.example.unieventos.ui.components.CouponItem
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TicketItem
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.TicketViewModel

@Composable
fun PurchasesScreen(
    paddingValues: PaddingValues,
    ticketViewModel: TicketViewModel,
    eventsViewModel: EventsViewModel,
    userId: String
) {

    var tickets by remember { mutableStateOf( listOf<Ticket>() )}

    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(userId) {
        try {
            isLoading = true
            tickets = ticketViewModel.getUserCartById(userId)
        } catch (e: Exception) {
            error = e.message
            Log.e("PurchasesScreen", "Error loading tickets", e)
        } finally {
            isLoading = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            error != null -> {
                Text(
                    text = error ?: "Unknown error occurred",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            tickets.isEmpty() -> {
                Text(
                    text = "No tickets found",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 7.dp,
                            end = 7.dp,
                            top = 90.dp,
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                ) {
                    items(tickets) { ticket ->
                        TicketItem(
                            ticket,
                            eventsViewModel = eventsViewModel
                        )
                    }
                }

            }
        }
    }


}
