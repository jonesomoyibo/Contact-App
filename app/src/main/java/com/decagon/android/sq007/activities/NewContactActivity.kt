package com.decagon.android.sq007.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.R

class NewContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_contact)

        if (intent.getStringExtra("ACTIVITY_NAME") == ContactProfileActivity::class.java.name) {
            initViewsValues()
        }
        registerViewsListeners()
    }

    private fun registerViewsListeners() {

        val backButton = findViewById<ImageView>(R.id.backbuttonicon)
        backButton.setOnClickListener({
            val contactIntent = Intent(this, MainActivity::class.java)
            startActivity(contactIntent)
        })

        val saveButtonView: TextView = findViewById(R.id.savebuttonview)
        saveButtonView.setOnClickListener({
        })
    }

    private fun initViewsValues() {

        val firstNameEditView = findViewById<EditText>(R.id.firstnameeditview)
        firstNameEditView.setText(intent.getStringExtra("CONTACTFIRSTNAME"))
        val lastNameEditView = findViewById<TextView>(R.id.lastnameeditview)
        lastNameEditView.text = intent.getStringExtra("CONTACTLASTNAME") as String
        val phoneNumberEditView = findViewById<EditText>(R.id.numbereditview)
        phoneNumberEditView.setText(intent.getStringExtra("CONTACTNUMBER"))
    }
}
