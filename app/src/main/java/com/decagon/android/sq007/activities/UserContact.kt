package com.decagon.android.sq007.activities

data class UserContact(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val source: String,
    var contactId: String?
)
