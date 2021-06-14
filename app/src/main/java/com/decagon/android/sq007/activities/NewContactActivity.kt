package com.decagon.android.sq007.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.R

class NewContactActivity : AppCompatActivity() {

    private lateinit var firstNameEditView: EditText
    private lateinit var lastNameEditView: EditText
    private lateinit var phoneNumberEditView: EditText
    private lateinit var emailEditView: EditText
    private lateinit var spinnerView:Spinner
    private lateinit var storageLocation:String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        initViews()
        if (intent.getStringExtra("ACTIVITY_NAME") == ContactProfileActivity::class.java.name) {
            setViewValues()
        }

        registerViewsListeners()
    }

    private fun registerViewsListeners() {

        val backButton = findViewById<ImageView>(R.id.backbuttonicon)
        backButton.setOnClickListener{
            // val contactIntent = Intent(this, MainActivity::class.java)
            // startActivity(contactIntent)
            onBackPressed()
        }

        val saveButtonView: TextView = findViewById(R.id.savebuttonview)
        saveButtonView.setOnClickListener{
            if(spinnerView.selectedView.getTag(spinnerView.selectedView.id).equals("${spinnerView.selectedView.id}0")){
                storageLocation="firebase"
            }
            else if(spinnerView.selectedView.getTag(spinnerView.selectedView.id).equals("${spinnerView.selectedView.id}1"))
            { storageLocation="phone"}
            if (intent.getStringExtra("ACTIVITY_NAME") == ContactProfileActivity::class.java.name) {
                updateContact(
                    UserContact(
                        firstNameEditView.text.toString(), lastNameEditView.text.toString(),
                        phoneNumberEditView.text.toString(), "F", email = emailEditView.text.toString(),
                        contactId = intent.getStringExtra("CONTACTID")!!
                    )
                )
            } else {
                addContact(
                    UserContact(
                        firstNameEditView.text.toString(), lastNameEditView.text.toString(),
                        phoneNumberEditView.text.toString(), "F", email = emailEditView.text.toString()
                    )
                )
            }
        }
    }

    private fun initViews() {
        firstNameEditView = findViewById(R.id.firstnameeditview)

        lastNameEditView = findViewById(R.id.lastnameeditview)

        phoneNumberEditView = findViewById(R.id.numbereditview)

        emailEditView = findViewById(R.id.emaileditview)

        spinnerView = findViewById<Spinner>(R.id.spinner)
        spinnerView.adapter=MySpinnerAdapter(this, getResources().getIntArray(R.array.imageid)
            ,getResources().getStringArray(R.array.location))
    }

    private fun addContact(contact: UserContact) {
        Log.i("vest","${storageLocation.equals("firebase")}")
      if(storageLocation.equals("firebase")){

        FireBaseManager.addContact(contact)}
        Toast.makeText(this, "Contact has been added", Toast.LENGTH_SHORT)
        val contactIntent = Intent(this@NewContactActivity, ContactProfileActivity::class.java)
        contactIntent.apply {
            putExtra("CONTACTNUMBER", phoneNumberEditView.text.toString())
            putExtra("CONTACTLASTNAME", lastNameEditView.text.toString())
            putExtra("CONTACTFIRSTNAME", firstNameEditView.text.toString())
        }
        startActivity(contactIntent)
    }

    private fun updateContact(contact: UserContact) {
        if(storageLocation.equals("firebase")){
        FireBaseManager.updateContact(contact)}
        val contactIntent = Intent(this@NewContactActivity, ContactProfileActivity::class.java)
        contactIntent.apply {
            putExtra("CONTACTNUMBER", contact.phoneNumber)
            putExtra("CONTACTLASTNAME", contact.lastName)
            putExtra("CONTACTFIRSTNAME", contact.firstName)
        }
        Toast.makeText(this, "Contact updated Successfully", Toast.LENGTH_SHORT)
        setResult(RESULT_OK, contactIntent)
    }

    private fun setViewValues() {
        firstNameEditView.setText(intent.getStringExtra("CONTACTFIRSTNAME"))
        lastNameEditView.setText(intent.getStringExtra("CONTACTLASTNAME"))
        phoneNumberEditView.setText(intent.getStringExtra("CONTACTNUMBER"))
    }
}
