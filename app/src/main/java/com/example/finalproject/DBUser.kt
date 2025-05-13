package com.example.finalproject

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DBUser(
    var email: String = "",
    var password: String = "",
    var pet: Int = -1
)