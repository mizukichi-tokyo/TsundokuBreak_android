package com.example.tsundokubreak.domain.service

import com.google.gson.GsonBuilder

object JsonHandler {

    val converter = GsonBuilder()
        .setLenient()
        .create()!!
}
