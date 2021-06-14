package com.decagon.android.sq007.activities

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.decagon.android.sq007.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var recentFragment: AppFragment
    private lateinit var contactFragment: AppFragment
    private lateinit var favouriteFragment: AppFragment
    private lateinit var fragment: AppFragment
    private val CONTACT_READ_REQUEST = 102

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        checkForPermissions(Manifest.permission.READ_CONTACTS, CONTACT_READ_REQUEST)
        FireBaseManager.loadFireBaseContacts()
        createFragments()
        registerViewsListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // creates the different fragments needed
    private fun createFragments() {

        recentFragment = AppFragment.newInstance(R.layout.fragment_recent)
        contactFragment = AppFragment.newInstance(R.layout.fragment_contact)
        favouriteFragment = AppFragment.newInstance(R.layout.fragment_favourite)
    }

    private fun registerViewsListeners() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomnavview)
        bottomNavigationView.apply {

            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.recents -> fragment = recentFragment
                    R.id.contacts -> fragment = contactFragment
                    R.id.favourites -> fragment = favouriteFragment
                }
                // call this method to show the fragment
                showFragment(fragment)
                return@setOnNavigationItemSelectedListener true
            }

            // Sets the default selected menu item on startup
            setSelectedItemId(R.id.contacts)
        }
    }

    // This replaces the current fragment with another fragment with the specified layout id
    private fun showFragment(fragment: AppFragment) {

        supportFragmentManager.beginTransaction().replace(R.id.fragmentcontainer, fragment)
            .commit()
    }

    private fun checkForPermissions(permissionType: String, requestCode: Int) {

        if (ContextCompat.checkSelfPermission(this, permissionType) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission to access the phone contacts

            ActivityCompat.requestPermissions(
                this,
                arrayOf(permissionType),
                requestCode
            )
        } else {
            // Check if phoneList has already been populated
            if (ContactRepository.getPhoneContactList().isEmpty()) {
                Toast.makeText(
                    this,
                    "Call Permission Granted",
                    Toast.LENGTH_SHORT
                ).show()
                readPhoneContacts()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACT_READ_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                readPhoneContacts()
            } else {
                Toast.makeText(
                    this,
                    "Call Permission Denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun contentReadContact(): Cursor? {
        return this.contentResolver?.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
    }

    private fun readPhoneContacts() {
       // ContactRepository.getPhoneContactList().clear()
        val contacts = contentReadContact()
        var count = 0
        while (contacts?.moveToNext()!! && count <10) {
            val name =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val mobileNumber =
                contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val contactObject = UserContact()
            contactObject.firstName = name
            contactObject.phoneNumber = mobileNumber
            contactObject.source = "P"
            ContactRepository.getPhoneContactList().add(contactObject)
            count++
        }
    }
}
