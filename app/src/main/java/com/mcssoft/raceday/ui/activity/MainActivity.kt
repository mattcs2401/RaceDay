package com.mcssoft.raceday.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mcssoft.raceday.R
import com.mcssoft.raceday.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TAG","MainActivity.onCreate")

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar.
        setSupportActionBar(binding.idToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Navigation.
        navController = Navigation.findNavController(this, R.id.id_nav_host_fragment)


//        bottomNavView = binding.idBottomNavView
//        NavigationUI.setupWithNavController(bottomNavView, navController)
//        bottomNavView.setOnNavigationItemSelectedListener(this)

        // Back Navigation.
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG","MainActivity.onStart")

    }

    override fun onStop() {
        super.onStop()
        Log.d("TAG","MainActivity.onStop")

    }
}