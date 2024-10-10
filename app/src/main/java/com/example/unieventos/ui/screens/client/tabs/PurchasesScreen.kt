package com.example.unieventos.ui.screens.client.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unieventos.ui.components.CouponItem
import com.example.unieventos.ui.components.TicketItem
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
                modifier = Modifier,
                eventsViewModel = eventsViewModel
            )
        }
    }
}