package com.decagon.android.sq007.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.decagon.android.sq007.R

class ContactProfileActivity : AppCompatActivity() {

    private lateinit var backButtonView: ImageView
    private lateinit var profileEditButton: androidx.appcompat.widget.Toolbar
    private lateinit var profilePhoneIcon: ImageView
    private lateinit var messageIcon: ImageView
    private lateinit var customMessageIcon: ImageView
    private val CALL_REQUEST_CODE = 100
    private val MESSAGE_REQUEST_CODE = 101
    private val CALL_ACTION = "call"
    private val MESSAGE_ACTION = "message"
    private lateinit var profileNumberView: TextView
    private lateinit var profileName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_profile)

        initViewsValues()
        registerViewsListeners()
    }

    private fun initViewsValues() {

        profileNumberView = findViewById<TextView>(R.id.profilenumber)
        profileNumberView.text = intent.getStringExtra("CONTACTNUMBER")
        profileName = findViewById<TextView>(R.id.profilename)
        profileName.text = "${intent.getStringExtra("CONTACTFIRSTNAME")} ${intent.getStringExtra("CONTACTLASTNAME")}"
        backButtonView = findViewById(R.id.profilebackbutton)
        profileEditButton = findViewById(R.id.profiletoolbar)
        profilePhoneIcon = findViewById(R.id.profilephone)
        messageIcon = findViewById(R.id.messageid)
        customMessageIcon = findViewById(R.id.messagecustom)


    }

    private fun registerViewsListeners() {

        messageIcon.apply {

            setOnClickListener({ sendMessage() })
        }

        customMessageIcon.apply {
            setOnClickListener({ sendMessage() })
        }

        profilePhoneIcon.apply {

            setOnClickListener({
                makeCall()
            })
        }

        backButtonView.apply {

            setOnClickListener {
                // val contactIntent = Intent(this@ContactProfileActivity, MainActivity::class.java)
                // startActivity(contactIntent)
                onBackPressed()
            }
        }

        profileEditButton.apply {

            setOnMenuItemClickListener {
                when (it.itemId) {

                    R.id.edit -> editContact()

                    R.id.share -> shareContact()

                    R.id.delete -> deleteContact(UserContact(contactId = intent.getStringExtra("CONTACTID")!!))
                }

                return@setOnMenuItemClickListener true
            }
        }
    }

    private fun makeCall() {
        checkForPermissions(Manifest.permission.CALL_PHONE, CALL_ACTION, CALL_REQUEST_CODE)
    }

    private fun sendIntentToExternalActivity(actionType: String) {
        var actionIntent: Intent
        if (actionType == CALL_ACTION) {
            actionIntent = Intent(Intent.ACTION_CALL)
            actionIntent.data = Uri.parse("tel:${profileNumberView.text}")
        } else {
            val uriSms = Uri.parse("smsto:08089388420")
            actionIntent = Intent(Intent.ACTION_SENDTO, uriSms)
            actionIntent.putExtra("sms_body", "The SMS text")
        }
        startActivity(actionIntent)
    }

    private fun shareContact() {

        val shareContactIntent = Intent()
        shareContactIntent.action = Intent.ACTION_SEND
        shareContactIntent.type = "text/plain"
        shareContactIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Name: ${
            profileName.text
            }\n Phone: ${profileNumberView.text}"
        )
        startActivity(Intent.createChooser(shareContactIntent, getString(R.string.number)))
        true
    }

    private fun sendMessage() {
        checkForPermissions(Manifest.permission.SEND_SMS, MESSAGE_ACTION, MESSAGE_REQUEST_CODE)
    }

    private fun deleteContact(contact: UserContact) {
        FireBaseManager.deleteContact(contact)
        // val contactIntent = Intent(this@ContactProfileActivity, MainActivity::class.java)

        // startActivity(contactIntent)
        Toast.makeText(this, "Contact has been deleted successfully", Toast.LENGTH_SHORT)
        onBackPressed()
    }

    private fun checkForPermissions(permissionType: String, actionType: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(this@ContactProfileActivity, permissionType) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            if (actionType == CALL_ACTION) {
                ActivityCompat.requestPermissions(
                    this@ContactProfileActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    requestCode
                )
            } else {
                ActivityCompat.requestPermissions(
                    this@ContactProfileActivity,
                    arrayOf(Manifest.permission.SEND_SMS),
                    requestCode
                )
            }
        } else {
            sendIntentToExternalActivity(actionType)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CALL_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendIntentToExternalActivity(CALL_ACTION)
            } else {
                Toast.makeText(
                    this@ContactProfileActivity,
                    "Call Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else if (requestCode == MESSAGE_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendIntentToExternalActivity(MESSAGE_ACTION)
            } else {
                Toast.makeText(
                    this@ContactProfileActivity,
                    "Message Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun editContact() {

        val contactIntent = Intent(this@ContactProfileActivity, NewContactActivity::class.java)
        contactIntent.putExtra("ACTIVITY_NAME", ContactProfileActivity::class.java.name)
        contactIntent.putExtra("CONTACTFIRSTNAME", intent.getStringExtra("CONTACTFIRSTNAME"))
        contactIntent.putExtra("CONTACTNUMBER", intent.getStringExtra("CONTACTNUMBER"))
        contactIntent.putExtra("CONTACTLASTNAME", intent.getStringExtra("CONTACTLASTNAME"))
        contactIntent.putExtra("CONTACTID", intent.getStringExtra("CONTACTID"))
        startActivityForResult(contactIntent, 1, null)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            profileNumberView.text = data?.getStringExtra("CONTACTNUMBER")
            profileName.text = "${data?.getStringExtra("CONTACTFIRSTNAME")} ${data?.getStringExtra("CONTACTLASTNAME")}"
        }
    }
}
