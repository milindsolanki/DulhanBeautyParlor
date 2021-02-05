package com.example.dulhanbeautyparlor.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar

import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.dulhanbeautyparlor.BasicActivity
import com.example.dulhanbeautyparlor.R
import com.example.dulhanbeautyparlor.R.id.bd_frame
import com.example.dulhanbeautyparlor.commenUtil.USER_EMAIL
import com.example.dulhanbeautyparlor.fragment.PortFolioFragment
import com.example.dulhanbeautyparlor.fragment.HomeFragment
import com.example.dulhanbeautyparlor.fragment.ServicesFragment
import com.example.dulhanbeautyparlor.fragment.TipsFragment
import com.example.dulhanbeautyparlor.interfaces.ButtonOk
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*
import com.example.dulhanbeautyparlor.commenUtil.PreferenceHelper.get

class MainActivity : BasicActivity(), ButtonOk {

    var name: String? = ""
    var email: String? = ""
    var photoUrl: Uri? = null

    var toolbar: Toolbar? = null
    lateinit var fragment: Fragment
    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment = HomeFragment()
        replacefragment(fragment)
        bottomnavigation.setOnNavigationItemSelectedListener(bottomView)
        navigation_drawer.setNavigationItemSelectedListener(navigationView)
        intiView()
    }

    private fun replacefragment(fragment: Fragment?) {
        if (fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.bd_frame, fragment).commit()
        }
    }

    private fun intiView() {


        toolbar = findViewById<Toolbar>(R.id.toolbar_app)

        toolbar!!.title = resources.getString(R.string.home)
        /*navtitle = findViewById(R.id.txt_name) as AppCompatTextView
        val title = sharPref[USER_EMAIL, ""]
        if (title != "")
            txt_name!!.text = title*/
        // userDetail()
        val dd = findViewById<DrawerLayout>(R.id.drawer_layout)
        toolbar!!.setNavigationIcon(R.drawable.toolbar_icon)
        val toggle =
            ActionBarDrawerToggle(this, dd, toolbar, R.string.nav_open, R.string.nav_close)

        dd.addDrawerListener(toggle)
        toggle.syncState()
    }

    private val bottomView = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.bd_home -> {
                toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_app)
                toolbar!!.title = resources.getString(R.string.home)
                fragment = HomeFragment()
                replacefragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bd_services -> {
                toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_app)
                toolbar!!.title = resources.getString(R.string.services)
                fragment = ServicesFragment()
                replacefragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bd_portfolio -> {
                toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_app)
                toolbar!!.title = resources.getString(R.string.portfolio)
                fragment = PortFolioFragment()
                replacefragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.bd_tips -> {
                toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_app)
                toolbar!!.title = resources.getString(R.string.tips)
                fragment = TipsFragment()
                replacefragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }

        return@OnNavigationItemSelectedListener false
    }

    private val navigationView = NavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                toolbar!!.title = resources.getString(R.string.home)
                fragment = HomeFragment()
                replacefragment(fragment)
                val mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
                mDrawerLayout.closeDrawers()
            }
            R.id.nav_about -> {
                startActivity(Intent(this, AboutUsActivity::class.java))
            }
            R.id.nav_appoinment -> {
                startActivity(Intent(this, AppointmentActivity::class.java))
            }
            R.id.nav_our_experts -> {
                startActivity(Intent(this, OurExpertsActivity::class.java))
            }
            R.id.nav_reviews -> {
                startActivity(Intent(this, ReviewsActivity::class.java))
            }
            R.id.nav_feedback -> {
                startActivity(Intent(this, FeedbackActivity::class.java))

            }
            R.id.nav_log_out -> {
                dialogBuilder("Are you sure you want to log out?", this)

            }
        }
        return@OnNavigationItemSelectedListener true
    }

    private fun userDetail() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            name = user.displayName
            email = user.email
            photoUrl = user.photoUrl

            // Set Data
            txt_name.text = name


            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project.
            val uid = user.uid
        }
    }

    override fun onOkClick() {
        val fAuth = FirebaseAuth.getInstance()
        fAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}