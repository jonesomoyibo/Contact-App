package com.decagon.android.sq007.activities

import android.util.Log
import java.util.ArrayList

object ContactRepository {

    private val firebaseList: ArrayList<UserContact> = ArrayList()
    private val phoneContactList: ArrayList<UserContact> = ArrayList()

    private val contactViewlist: ArrayList<UserContact> = ArrayList()

    fun getContactList(): ArrayList<UserContact> {
        contactViewlist.apply {
            addAll(phoneContactList)
            addAll(firebaseList)
            Log.i("Checking", "${ContactRepository.getFireBaseList()}")
        }
        return contactViewlist
    }



    fun getFireBaseList(): ArrayList<UserContact> {

        return firebaseList
    }

    fun getPhoneContactList(): ArrayList<UserContact> {

        return phoneContactList
    }


}