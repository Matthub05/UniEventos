package com.example.unieventos.ui.screens.client.tabs

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.Ticket
import com.example.unieventos.ui.components.CouponItem
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TicketItem
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.TicketViewModel

@Composable
fun PurchasesScreen(
    paddingValues: PaddingValues,
    ticketViewModel: TicketViewModel,
    eventsViewModel: EventsViewModel
) {
    val tickets = ticketViewModel.ticket.collectAsState().value

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
