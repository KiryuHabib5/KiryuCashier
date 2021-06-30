package com.magangonline.kiryucashier.feature.Home.Dashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.magangonline.kiryucashier.R
import com.magangonline.kiryucashier.feature.Home.Profile.ProfileActivity
import com.magangonline.kiryucashier.feature.addproduk.AddProdukActivity
import kotlinx.android.synthetic.main.nav_header_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var  auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
//        toolbar.setLogo(R.drawable.logo2)
//        val fab: FloatingActionButton = findViewById(R.id.fab)
//        fab.setOnClickListener {
////                view ->
////            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                    .setAction("Action", null).show()
//            val gotoAdd = Intent(this, AddProdukActivity::class.java)
//            startActivity(gotoAdd)
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_riwayat, R.id.nav_transaksi), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        auth = Firebase.auth
        val currentUser = auth?.currentUser
        //tv_email.text = currentUser?.email.toString()
        val view = navView.getHeaderView(0)
        view.tv_email.text = currentUser?.email.toString()

        view.tv_email.setOnClickListener {
            val goToProfile = Intent(this, ProfileActivity::class.java)
            startActivity(goToProfile)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}