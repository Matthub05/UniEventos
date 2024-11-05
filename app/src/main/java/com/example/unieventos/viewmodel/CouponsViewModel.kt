package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.Coupon
import com.example.unieventos.models.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Calendar

class CouponsViewModel:ViewModel() {

    private val db = Firebase.firestore
    private val collectionPathName = "coupons"
    private val _coupon = MutableStateFlow( emptyList<Coupon>() )
    val coupon: StateFlow< List<Coupon> > = _coupon.asStateFlow()

    init {
        loadCoupons()
    }

    private fun loadCoupons() {
        viewModelScope.launch {
            _coupon.value = getCoupons()
        }
    }

    private suspend fun getCoupons(): List<Coupon> {
        val snapshot = db.collection(collectionPathName).get().await()
        return snapshot.documents.mapNotNull {
            val coupon = it.toObject(Coupon::class.java)
            requireNotNull(coupon)
            coupon.id = it.id
            coupon
        }
    }

    fun createCoupon(coupon: Coupon) {
        viewModelScope.launch {
            db.collection(collectionPathName).add(coupon).await()
            _coupon.value = getCoupons()
        }
    }

    fun deleteCoupon(couponId: String) {
        viewModelScope.launch {
            db.collection(collectionPathName).document(couponId).delete().await()
            _coupon.value = getCoupons()
        }
    }

    suspend fun getCouponById(id: String): Coupon? {
        val snapshot = db.collection(collectionPathName).document(id).get().await()
        val coupon = snapshot.toObject(Coupon::class.java)
        coupon?.id = snapshot.id
        return coupon
    }

    fun searchCoupons(query: String): List<Coupon> {
        return _coupon.value.filter { it.code.contains(query, ignoreCase = true) }
    }


//    private fun getCoupons(): List<Coupon> {
//        val cal: Calendar = Calendar.getInstance()
//        cal.set(2024, 9, 2)
//        val inicio = cal.time
//        cal.set(2024, 10, 3)
//        val fin = cal.time
//        return listOf(
//            Coupon(
//                id = "1",
//                description = "Dia de las madres",
//                code = "code1",
//                startDate = inicio,
//                endDate = fin,
//                discount = 10.0
//            ),
//            Coupon(
//                id = "2",
//                description = "Dia de los padres",
//                code = "code2",
//                startDate = inicio,
//                endDate = fin,
//                discount = 15.0
//            ),
//            Coupon(
//                id = "3",
//                description = "Dia del agua",
//                code = "code3",
//                startDate = inicio,
//                endDate = fin,
//                discount = 20.0
//            ),
//            Coupon(
//                id = "4",
//                description = "Que la fuerza te acompañe",
//                code = "code4",
//                startDate = inicio,
//                endDate = fin,
//                discount = 25.0
//            )
//        )
//    }

}