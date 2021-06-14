package com.decagon.android.sq007.activities

import android.content.ContentValues.TAG
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import java.util.*

const val CONTACT_DATABASE = "contacts"

object FireBaseManager {

    private val mutableLiveData = MutableLiveData<Exception?>()
    private val dbContacts: DatabaseReference = FirebaseDatabase.getInstance().getReference(CONTACT_DATABASE)

    fun addContact(contact: UserContact) {
        contact.contactId = dbContacts.push().key!!
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

    fun loadFireBaseContacts() {
        Log.i("Checking", " He said")
        ContactRepository.getFireBaseList().clear()
        dbContacts.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var data: UserContact
                if (dataSnapshot.exists() != false) {
                    for (snapShot in dataSnapshot.children) {
                        data = snapShot.getValue(UserContact::class.java)!!
                        data.apply {
                            if (source.equals(""))source = "F"
                        }
                        ContactRepository.getFireBaseList().add(data)
                        Log.i("Checking", "${ContactRepository.getFireBaseList()}")

                    }


                }
                AppFragment.getRecyclerView().adapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(errorObject: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", errorObject.toException())
            }
        })
    }
}
