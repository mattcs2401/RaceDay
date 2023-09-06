package com.mcssoft.racedaybasic.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <VM: ViewModel> vmFactory(initialiser:() -> VM): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>/*, extras: CreationExtras*/): T {
            return initialiser as T
        }
    }
}
