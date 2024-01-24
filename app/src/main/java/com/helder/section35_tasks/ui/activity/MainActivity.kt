package com.helder.section35_tasks.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.material.navigation.NavigationView
import com.helder.section35_tasks.R
import com.helder.section35_tasks.databinding.ActivityMainBinding
import com.helder.section35_tasks.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.baseLayout.baseLayoutToolbar)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        navView = binding.navView

        val navHost =
            supportFragmentManager.findFragmentById(binding.baseLayout.fragmentContainerView.id)
                    as NavHostFragment
        navController = navHost.navController

        drawerLayout = binding.drawerLayout

        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.baseLayout.baseLayoutToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        onBackPressedDispatcher.addCallback {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }

        navView.setCheckedItem(R.id.all_tasks)
        navView.setNavigationItemSelectedListener(this)

        viewModel.loadUserName()

        observe()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.all_tasks -> {
                navController.navigate(R.id.all_tasks_fragment)
            }

            R.id.tasks_in_next_seven_days -> {
                navController.navigate(R.id.next_seven_days_tasks_fragment)
            }

            R.id.expired_tasks -> {
                navController.navigate(R.id.expired_tasks_fragment)
            }

            R.id.logout -> {
                //Change this to a navigation action later
                viewModel.doLogout()
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)

        return true
    }

    private fun observe() {
        viewModel.logout.observe(this) {
            if(it) {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
            }
        }

        viewModel.userName.observe(this) {
            val header = binding.navView.getHeaderView(0)
            header.findViewById<TextView>(R.id.text_name).text = it
        }
    }

}