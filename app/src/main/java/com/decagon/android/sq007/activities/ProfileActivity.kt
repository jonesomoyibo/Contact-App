package com.decagon.android.sq007.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.decagon.android.sq007.R

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}
