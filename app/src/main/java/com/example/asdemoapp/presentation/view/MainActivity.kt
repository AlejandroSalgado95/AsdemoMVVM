package com.example.asdemoapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import com.example.asdemoapp.R
import com.example.asdemoapp.databinding.ActivityMainBinding
import com.example.asdemoapp.databinding.FragmentApiListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val navController by lazy { (supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment).navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.SplashScreenTheme)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.bottomNavMenu.visibility = when (destination.id) {
                R.id.list, R.id.profile, R.id.super_hero_search -> View.VISIBLE
                else -> View.GONE
            }
        }
        appBarConfiguration = AppBarConfiguration(setOf(R.id.list, R.id.super_hero_search, R.id.profile))
        binding.bottomNavMenu.setupWithNavController(navController)
        binding.bottomNavMenu.setOnItemReselectedListener {}

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount
        if(backStackEntryCount == 0){
            this.finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}