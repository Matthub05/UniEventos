package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unieventos.models.Coupon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class CouponsViewModel:ViewModel() {

    private val _coupon = MutableStateFlow( emptyList<Coupon>() )
    val coupon: StateFlow< List<Coupon> > = _coupon.asStateFlow()

    init {
        _coupon.value = getCoupons()
    }

    fun createCoupon(coupon: Coupon) {
        coupon.id = (_coupon.value.size + 1).toString()
        _coupon.value += coupon
    }

    fun deleteCoupon(coupon: Coupon) {
        _coupon.value -= coupon
    }

    fun getCouponById(id: String): Coupon? {
        return _coupon.value.find { it.id == id }
    }

    fun searchCoupons(code: String): List<Coupon> {
        return _coupon.value.filter { it.code.contains(code, ignoreCase = true) }
    }

    private fun getCoupons(): List<Coupon> {
        val cal: Calendar = Calendar.getInstance()
        cal.set(2024, 9, 2)
        val inicio = cal.time
        cal.set(2024, 10, 3)
        val fin = cal.time
        return listOf(
            Coupon(
                id = "1",
                description = "Dia de las madres",
                code = "code1",
                startDate = inicio,
                endDate = fin,
                discount = 10.0
            ),
            Coupon(
                id = "2",
                description = "Dia de los padres",
                code = "code2",
                startDate = inicio,
                endDate = fin,
                discount = 15.0
            ),
            Coupon(
                id = "3",
                description = "Dia del agua",
                code = "code3",
                startDate = inicio,
                endDate = fin,
                discount = 20.0
            ),
            Coupon(
                id = "4",
                description = "Que la fuerza te acompañe",
                code = "code4",
                startDate = inicio,
                endDate = fin,
                discount = 25.0
            )
        )
    }

}