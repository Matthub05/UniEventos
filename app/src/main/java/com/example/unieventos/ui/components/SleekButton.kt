package com.example.unieventos.ui.components

import android.graphics.drawable.Icon
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.utils.SharedPreferenceUtils

@Composable
fun SleekButton(
    text: String,
    onClickAction: () -> Unit,
) {
    Button(
        modifier = Modifier
            .width(318.dp)
            .height(50.dp),
        shape = RoundedCornerShape(4.dp),
        onClick = {
            onClickAction
        }
    ) {
        Text(text = text)
    }
}