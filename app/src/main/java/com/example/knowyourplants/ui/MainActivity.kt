package com.example.knowyourplants.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.knowyourplants.R
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var viewPager: ViewPager2
    private lateinit var navController: NavController
    private var viewPagerPageChangeCallback : ViewPager2.OnPageChangeCallback? = null
    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewPager = findViewById(R.id.pager)
        bottomNav = findViewById(R.id.bottom_navigation)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
        setUpViewPagerWithBottomNav()
    }

    /**
     * Function to setup view pager and keep viewpager and bottom navigation in sync
     * */
    private fun setUpViewPagerWithBottomNav(){

        // callback to sync navigation component/bottom nav with viewpager selected page
        viewPagerPageChangeCallback = object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> if (navController.currentDestination?.id != R.id.fragment_home) {
                        navController.navigate(R.id.fragment_home, null, navOptions {
                            popUpTo(R.id.fragment_home) { inclusive = false }
                            launchSingleTop = true
                        })
                    }
                    1 -> if (navController.currentDestination?.id != R.id.fragment_search) {
                        navController.navigate(R.id.fragment_search, null, navOptions {
                            popUpTo(R.id.fragment_home) { inclusive = false }
                        })
                    }
                    2 -> if (navController.currentDestination?.id != R.id.fragment_account) {
                        navController.navigate(R.id.fragment_account, null, navOptions {
                            popUpTo(R.id.fragment_home) { inclusive = false }
                        })
                    }
                }
            }
        }
        viewPagerPageChangeCallback?.let { viewPager.registerOnPageChangeCallback(it) }

        // dummy adapter
        viewPager.adapter = object: FragmentStateAdapter(this) {
            override fun getItemCount(): Int = 3
            override fun createFragment(position: Int): Fragment = Fragment()
        }

        syncViewPagerBottomNav()
    }

    /**
     *  Function to sync bottom nav current tab with ViewPager current selected tab
     * */
    private fun syncViewPagerBottomNav() {
        // listening to destination change to sync viewpager  with back popUp navigation + enable full screen opening of other screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val targetPage =  when (destination.id) {
                R.id.fragment_home -> 0
                R.id.fragment_search -> 1
                R.id.fragment_account -> 2
                else -> -1
            }
            if(targetPage==-1) {
                bottomNav.visibility = View.GONE
                viewPager.isUserInputEnabled = false // disable viewpager
            } else if(!viewPager.isUserInputEnabled){
                bottomNav.visibility = View.VISIBLE
                viewPager.isUserInputEnabled = true // enable viewpager
            }
            if (targetPage!=-1 && viewPager.currentItem != targetPage) {
                viewPager.currentItem = targetPage
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.fragment_home -> {
                    if(viewPager.currentItem!=0)
                        viewPager.currentItem = 0
                    return@setOnItemSelectedListener true
                }
                R.id.fragment_search -> {
                    if(viewPager.currentItem!=1)
                        viewPager.currentItem = 1
                    return@setOnItemSelectedListener true
                }
                R.id.fragment_account -> {
                    if(viewPager.currentItem!=2)
                        viewPager.currentItem = 2
                    return@setOnItemSelectedListener true
                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewPagerPageChangeCallback?.let { viewPager.unregisterOnPageChangeCallback(it) }
    }
}