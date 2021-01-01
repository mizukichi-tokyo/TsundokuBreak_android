package com.example.tsundokubreak.ui.dokuryo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DokuryoViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Dokuryo Fragment"
    }
    val text: LiveData<String> = _text
}
