package com.example.drinksrecipes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.drinksrecipes.databinding.ActivityMainBinding
import com.example.drinksrecipes.fragments.favourite.FavouriteFragment
import com.example.drinksrecipes.fragments.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityBinding: ActivityMainBinding

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)

        //initialize binding
        mainActivityBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        setSupportActionBar(mainActivityBinding.mainToolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)


        setBottomNavigationView()
        loadFragment(HomeFragment())

    }






    private fun setBottomNavigationView(){

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home ->{

                    replaceNewFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }

                R.id.nav_favourite ->{
                    replaceNewFragment(FavouriteFragment())
                    return@OnNavigationItemSelectedListener true
                }

                else -> true
            }
            })

    }

    /********************************************************************************************************************/
    //                                          Fragments Attachments Section
    /*******************************************************************************************************************/

    private fun loadFragment(fragment: Fragment?) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .add(R.id.fragment_container, fragment!!)
            .commit()
    }



    fun replaceNewFragment(fragment: Fragment?) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        );
        transaction.replace(R.id.fragment_container, fragment!!)
        transaction.commit()
    }

}