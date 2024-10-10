package com.example.unieventos.ui.screens.admin

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.models.Coupon
import com.example.unieventos.ui.components.DatePickerForm

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.viewmodel.CouponsViewModel
import java.util.Calendar

@Composable
fun CreateCouponScreen(
    onNavigateToBack: () -> Unit,
    couponViewModel: CouponsViewModel
) {

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.titulo_generacion_cupon),
                onClick = { onNavigateToBack() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) { innerPadding ->

        CreateEventForm(
            padding = innerPadding,
            onNavigateToBack = onNavigateToBack,
            couponViewModel = couponViewModel,
            context = context
        )

    }

}

@Composable
fun CreateEventForm(
    padding: PaddingValues,
    onNavigateToBack: () -> Unit,
    couponViewModel: CouponsViewModel,
    context: Context
) {

    var code by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var discount by rememberSaveable { mutableIntStateOf(0) }
    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = code,
            onValueChange = { code = it },
            supportingText = stringResource(id = R.string.err_codigo),
            label = stringResource(id = R.string.placeholder_codigo_cupon),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = description,
            onValueChange = { description = it },
            supportingText = stringResource(id = R.string.err_descripcion),
            label = stringResource(id = R.string.placeholder_descripcion),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = discount.toString(),
            onValueChange = { discount = it.toIntOrNull() ?: 0},
            supportingText = stringResource(id = R.string.err_descuento),
            label = stringResource(id = R.string.placeholder_descuento_cupon),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        DatePickerForm(
            modifier = Modifier.fillMaxWidth(),
            value = startDate,
            onValueChange = { startDate = it },
            label = stringResource(id = R.string.placeholder_fecha_inicio)
        )

        DatePickerForm(
            modifier = Modifier.fillMaxWidth(),
            value = endDate,
            onValueChange = { endDate = it },
            label = stringResource(id = R.string.placeholder_fecha_vencimiento)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            fontSize = 11.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.adv_generar_cupon)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                val cal = Calendar.getInstance()
                var values = startDate.split("/")
                cal.set(values[2].toInt(), values[1].toInt() - 1, values[0].toInt())
                val startDateParsed = cal.time
                values = endDate.split("/")
                cal.set(values[2].toInt(), values[1].toInt() - 1, values[0].toInt())
                val endDateParsed = cal.time

                couponViewModel.createCoupon(
                    Coupon(
                        id = 0,
                        description = description,
                        code = code,
                        startDate = startDateParsed,
                        endDate = endDateParsed,
                        discount = discount.toDouble()
                    )
                )

                Toast.makeText(context, context.getString(R.string.coupon_created), Toast.LENGTH_SHORT).show()
                onNavigateToBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
        ) {
            Text(text = stringResource(id = R.string.btn_registrar_cupon))
        }

    }
}