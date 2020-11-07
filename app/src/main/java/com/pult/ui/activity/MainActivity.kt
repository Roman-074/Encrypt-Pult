package com.pult.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.pult.R
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bottom menu
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        bottom_navigation_view.setTextVisibility(false)
        bottom_navigation_view.enableItemShiftingMode(false)
        bottom_navigation_view.enableItemShiftingMode(false)
        bottom_navigation_view.enableAnimation(false)
        for (i in 0 until bottom_navigation_view.menu.size())
            bottom_navigation_view.setIconTintList(i, null)

        bottom_navigation_view.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_item_list -> navController?.navigate(R.id.listFragment)
                R.id.nav_item_key -> navController?.navigate(R.id.keyFragment)
                R.id.nav_item_sign -> navController?.navigate(R.id.signFragment)
                R.id.nav_item_upload -> navController?.navigate(R.id.uploadFragment)
            }
            true
        }

    }

}