package com.example.taskapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp.databinding.ActivityMainBinding
import com.example.taskapp.utils.MainApplication
import com.example.taskapp.utils.Preferences
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,
                R.id.newTaskFragment2,R.id.profileFragment, R.id.onBoardFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val preferences = Preferences(this)
        if (preferences.isHaveSeenOnBoarding()) {
            navController.navigate(
                R.id.navigation_home
            )
        } else {
            navController.navigate(
                R.id.onBoardFragment
            )
        }

        navController.addOnDestinationChangedListener{_, dest, _ ->
        navView.visibility =
            if (dest.id == R.id.newTaskFragment2 || dest.id == R.id.onBoardFragment) View.GONE else View.VISIBLE
        }
    }

}