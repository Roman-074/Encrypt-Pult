package com.pult.ui.fragment.sign

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pult.ui.encryption.RSA

class SignViewModel : ViewModel() {

    val keyOwn = MutableLiveData<String>()

    // вызывается после создания экземпляра класса
    init {
            keyOwn.value = "Private Key"
    }

    fun getNewKeys() {
        RSA.generateKeyPair()
    }


}