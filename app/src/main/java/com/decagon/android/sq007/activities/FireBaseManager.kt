package com.decagon.android.sq007.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

const val CONTACT_DATABASE = "contacts"
class FireBaseManager() {

    private var mutableLiveData: MutableLiveData<Exception?>
    private lateinit var liveData: LiveData<Exception?>
    private var dbContacts: DatabaseReference
    private val contactList: MutableLiveData<UserContact>
    private val contact: LiveData<UserContact>

    init {
        dbContacts = FirebaseDatabase.getInstance().getReference(CONTACT_DATABASE)
        mutableLiveData = MutableLiveData<Exception?>()
        liveData = mutableLiveData
        contactList = MutableLiveData<UserContact>()
        contact = contactList
    }

    fun addContact(contact: UserContact) {
        contact.contactId = dbContacts.push().key
        dbContacts.child(contact.contactId!!).setValue(contact).addOnCompleteListener {
            if (it.isSuccessful) {
                mutableLiveData.value = null
            } else {
                mutableLiveData.value = it.exception
            }
        }
    }

    fun updateContact(contact: UserContact) {
        dbContacts.child(contact.contactId!!).setValue(contact).addOnCompleteListener {
            if (it.isSuccessful) {
                mutableLiveData.value = null
            } else {
                mutableLiveData.value = it.exception
            }
        }
    }

    fun deleteContact(contact: UserContact) {
        dbContacts.child(contact.contactId!!).setValue(null)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    mutableLiveData.value = null
                } else {
                    mutableLiveData.value = it.exception
                }
            }
    }
}
