package com.tanavota.tanavota

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.tanavota.tanavota.view.Navigator
import com.tanavota.tanavota.view.history.HistoryFragment
import com.tanavota.tanavota.view.home.HomeFragment
import timber.log.Timber

class MainActivity : AppCompatActivity(), Navigator,
        NavigationView.OnNavigationItemSelectedListener {
    private val toolbar: Toolbar by lazy { findViewById<Toolbar>(R.id.toolbar) }
    private val navigationView: NavigationView by lazy { findViewById<NavigationView>(R.id.navigation_menu) }
    private val drawer: DrawerLayout by lazy { findViewById<DrawerLayout>(R.id.drawer_layout) }
    private val drawerToggle: ActionBarDrawerToggle by lazy {
        object : ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close) {

        }
    }
    val fragmentManager = supportFragmentManager

    fun getFrontFragment(): Fragment? = supportFragmentManager?.findFragmentById(R.id.container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
//        toolbar.inflateMenu(R.menu.menu)

        drawer.addDrawerListener(drawerToggle)

        // UPナビゲーションを有効化する
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            navigateToFragment(fragmentManager, HomeFragment.newInstance())
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Timber.d("[MainActivity] onNavigationItemSelected: $item")

        when (item.itemId) {
            R.id.menu_home -> {
                navigateToFragment(fragmentManager, HomeFragment.newInstance())
                drawer.closeDrawer(GravityCompat.START)
            }
            R.id.menu_history -> {
                navigateToFragment(fragmentManager, HistoryFragment.newInstance())
                drawer.closeDrawer(GravityCompat.START)
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item ?: return super.onOptionsItemSelected(item)

        Timber.d("[MainActivity] onOptionsItemSelected: $item")

        // ActionBarDrawerToggleにandroid.id.home(up ナビゲーション)を渡す。
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }

        when (item.itemId) {
            R.id.action_favorite -> {
//                if (getFrontFragment() is FavoriteFragment) {
//                    return true
//                }
//
//                fragmentNavigator.navigateToFragment(FavoriteFragment.createMenuFragment())
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onBackPressed() {

        val frontFragment = getFrontFragment()

        when {
            drawer.isDrawerOpen(GravityCompat.START) -> {
                drawer.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed()
            }

        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }
}
