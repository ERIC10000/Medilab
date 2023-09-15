package com.modcom.medilabmemberapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){

                R.id.bottom_search -> { replaceFragment(SearchFragment())}
                R.id.bottom_add -> { replaceFragment(AddDependantFragment())}
                R.id.bottom_cart -> { replaceFragment(CartFragment())}
                R.id.bottom_profile -> { replaceFragment(ProfileFragment())}

            } // end when
            true

        } // end listener

        replaceFragment(SearchFragment())

    } // end onCreate()

    // function to replace a Fragment based on menuItem
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu_home, menu)

        return true
    }

    // SET BADGE AND LOCATION SERVICES(GPS)


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.cart){
            val intent = Intent(applicationContext, MyCart::class.java)
            startActivity(intent)
        }

        return true
    }
}