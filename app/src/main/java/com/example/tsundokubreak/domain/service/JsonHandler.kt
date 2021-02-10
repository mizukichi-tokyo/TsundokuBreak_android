package com.example.tsundokubreak.domain.service

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder

object JsonHandler {

    val converter = GsonBuilder()
        .setLenient()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()!!
}
