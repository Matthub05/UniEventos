package com.example.unieventos.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun TextFieldForm(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    supportingText: String,
    label: String,
    onValidate: (String) -> Boolean,
    keyboardOptions: KeyboardOptions,
    isPassword: Boolean = false,
){

    var isHidden by rememberSaveable { mutableStateOf(true) }
    var isError by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        value = value,
        singleLine = true,
        isError = isError,
        supportingText = {
            if (isError) {
                Text(text = supportingText)
            }
        },
        visualTransformation = if(isPassword && isHidden){ PasswordVisualTransformation() } else { VisualTransformation.None },
        keyboardOptions = keyboardOptions,
        label = {
            Text(text = label)
        },
        onValueChange = {
            onValueChange(it)
            isError = onValidate(it)
        },
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { isHidden = !isHidden }) {
                    val visibilityIcon =
                        if (isHidden) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                    // Please provide localized description for accessibility services
                    val description = if (isHidden) "Show password" else "Hide password"
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            }
        }
    )
}