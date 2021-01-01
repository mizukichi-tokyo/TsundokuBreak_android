package com.example.tsundokubreak.ui.tsundoku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TsundokuViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Tsundoku Fragment"
    }
    val text: LiveData<String> = _text
}