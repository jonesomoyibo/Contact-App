package com.decagon.android.sq007.activities

import java.util.ArrayList

class ContactsFactory {

    private val contactViewlist: ArrayList<UserContact> = ArrayList()

    init {

//
//        contactViewlist.apply {
//            add(UserContact("Jones", "Omoyibo", "08168105123","F"))
//            add(UserContact("Jones Airtel", "Omoyibo", "08023344948","P"))
//            add(UserContact("Peace", "Igbadumhe", "08089388420","P"))
//            add(UserContact("Vinda", "Ifemi", "08168104629","F"))
//            add(UserContact("Abel", "Omoyibo", "08168104629","F"))
//            add(UserContact("Innocent", "Omoyibo", "08168104629","P"))
//            add(UserContact("Jane", "Omoyibo", "08168104629","F"))
//            add(UserContact("Peace", "Ezohomon", "08168104629","F"))
//            add(UserContact("KC", "Decagon", "08168104629","F"))
//            add(UserContact("Festus", "Decagon", "08168104629","P"))
//        }
    }

    fun getContactList(): ArrayList<UserContact> {

        return this.contactViewlist
    }
}
