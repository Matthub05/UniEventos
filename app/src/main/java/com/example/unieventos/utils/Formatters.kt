package com.example.unieventos.utils

import java.text.DecimalFormat

class Formatters {
    companion object {

        fun formatPrice(price: Double): String {
            val df = DecimalFormat("#,###.##")
            val formattedPrice = df.format(price)

            return "$formattedPrice $"
        }

        fun formatNumber(number: Int): String {
            val df = DecimalFormat("#,###")
            return df.format(number)
        }

    }
}