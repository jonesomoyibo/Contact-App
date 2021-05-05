package com.decagon.android.sq007.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var recentFragment: AppFragment
    private lateinit var contactFragment: AppFragment
    private lateinit var favouriteFragment: AppFragment
    private lateinit var fragment: AppFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        getSupportActionBar()!!.setDisplayShowTitleEnabled(false)
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
}
