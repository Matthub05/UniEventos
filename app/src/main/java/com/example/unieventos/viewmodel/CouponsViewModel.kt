package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.Coupon
import com.example.unieventos.models.Event
import com.example.unieventos.utils.RequestResult
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

    private val _authResult = MutableStateFlow<RequestResult?>(null)
    val authResult: StateFlow<RequestResult?> = _authResult.asStateFlow()

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
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { createCouponFirebase(coupon) }
                .fold(
                    onSuccess = { RequestResult.Success("Cupón Creado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun createCouponFirebase(coupon: Coupon) {
        viewModelScope.launch {
            db.collection(collectionPathName).add(coupon).await()
            _coupon.value = getCoupons()
        }
    }

    fun deleteCoupon(couponId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { deleteCouponFirebase(couponId) }
                .fold(
                    onSuccess = { RequestResult.Success("Cupón Eliminado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun deleteCouponFirebase(couponId: String) {
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

    fun resetAuthResult() {
        _authResult.value = null
    }

}