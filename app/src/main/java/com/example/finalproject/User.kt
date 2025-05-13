package com.example.finalproject

import com.google.firebase.database.IgnoreExtraProperties

//model class for the user object for the firebase database
@IgnoreExtraProperties
data class User(
    var email: String = "",
    var password: String = "",
    var pet: Int = -1
)