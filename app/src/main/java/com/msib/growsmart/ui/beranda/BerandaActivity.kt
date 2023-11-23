package com.msib.growsmart.ui.beranda

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.msib.growsmart.R
import com.msib.growsmart.databinding.ActivityBerandaBinding
import com.msib.growsmart.preference.UserPreference

class BerandaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBerandaBinding
    private lateinit var navController: NavController
    private lateinit var navView: BottomNavigationView
    private lateinit var menu: Menu
    private lateinit var preference: UserPreference
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBerandaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        preference = UserPreference.getInstance(dataStore)


        initBottomNav()
    }


    private fun initBottomNav() {
        navView = binding.navView
        menu = navView.menu
        navController = findNavController(R.id.nav_host_fragment_activity_beranda)
        navView.setupWithNavController(navController)
        navController.setGraph(R.navigation.app_navigation)

        when (intent.getStringExtra("action")) {
            "Beranda" -> {
                navController.navigate(R.id.navigation_beranda)
                menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda_selected)
                menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca)
                menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum)
                menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms)
            }
            "Cuaca" -> {
                navController.navigate(R.id.navigation_cuaca)
                menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda)
                menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca_selected)
                menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum)
                menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms)
            }
            "Forum" -> {
                navController.navigate(R.id.navigation_forum)
                menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda)
                menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca)
                menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum_selected)
                menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms)
            }
            else -> {
                navController.navigate(R.id.navigation_lms)
                menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda)
                menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca)
                menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum)
                menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms_selected)
            }
        }

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_beranda -> {
                    item.setIcon(R.drawable.ic_nav_beranda_selected)
                    menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca)
                    menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum)
                    menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms)
                    navController.navigate(R.id.navigation_beranda)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_cuaca -> {
                    item.setIcon(R.drawable.ic_nav_cuaca_selected)
                    menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda)
                    menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum)
                    menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms)
                    navController.navigate(R.id.navigation_cuaca)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_forum -> {
                    item.setIcon(R.drawable.ic_nav_forum_selected)
                    menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda)
                    menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca)
                    menu.findItem(R.id.navigation_lms).setIcon(R.drawable.ic_nav_lms)
                    navController.navigate(R.id.navigation_forum)
                    return@setOnItemSelectedListener true
                }

                R.id.navigation_lms -> {
                    item.setIcon(R.drawable.ic_nav_lms_selected)
                    menu.findItem(R.id.navigation_beranda).setIcon(R.drawable.ic_nav_beranda)
                    menu.findItem(R.id.navigation_cuaca).setIcon(R.drawable.ic_nav_cuaca)
                    menu.findItem(R.id.navigation_forum).setIcon(R.drawable.ic_nav_forum)
                    navController.navigate(R.id.navigation_lms)
                    return@setOnItemSelectedListener true
                }

                else -> {
                    return@setOnItemSelectedListener true
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context, value: String? = "") {
            val starter = Intent(context, BerandaActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtra("action", value)
            context.startActivity(starter)
        }
        const val PERMISSION_ID = 1000
    }
}