package com.sergiomeza.lyrics.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.sergiomeza.lyrics.R
import com.sergiomeza.lyrics.ui.utils.DialogLoader

class MainActivity : AppCompatActivity() {
    private var loaderDialog : AlertDialog?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_search, R.id.navigation_history
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun showLoading(){
        loaderDialog = DialogLoader.create(this)
        loaderDialog?.show()
    }

    fun hideLoading(){
        if (loaderDialog != null){
            loaderDialog?.dismiss()
        }
    }
}