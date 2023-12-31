package com.ssn.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.ssn.studentapp.fragment.AddSv
import com.ssn.studentapp.fragment.Book
import com.ssn.studentapp.fragment.Class
import com.ssn.studentapp.fragment.Details
import com.ssn.studentapp.fragment.Faculty
import com.ssn.studentapp.fragment.Home
import com.ssn.studentapp.fragment.Language
import com.ssn.studentapp.fragment.School
import com.ssn.studentapp.fragment.Student
import com.ssn.studentapp.fragment.StudentMn
import com.ssn.studentapp.fragment.navHome


class UserActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var school: School
    private lateinit var home: Home
    private lateinit var book: Book
    private lateinit var faculty: Faculty
    private lateinit var clazz: Class
    private lateinit var student: Student

    private lateinit var navigation: NavigationView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navHome: navHome
    private lateinit var addSv: AddSv
    private lateinit var details: Details
    private lateinit var studentMn: StudentMn
    private lateinit var username: TextView
    private lateinit var language: Language


    override fun onCreate(savedInstanceState: Bundle?) {

        school = School()
        home = Home()
        book = Book()
        faculty = Faculty()
        clazz = Class()
        student = Student()
        navHome = navHome()
        studentMn = StudentMn()
        details = Details()
        addSv = AddSv()
        language = Language()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        toolbar = findViewById(R.id.toolbar)
        navigation = findViewById(R.id.navigationId)
        drawerLayout = findViewById(R.id.drawyer)
        bottomNavigationView = findViewById(R.id.bottomMenu)
        val headerView = navigation.getHeaderView(0)
        username = headerView.findViewById(R.id.tvFirstName)
        username.text = "Admin!"
        toolbar.title = "StudentApp"

        setFragment(student)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this@UserActivity, drawerLayout, toolbar, R.string.open, R.string.close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.studentId -> {
                    setFragment(student)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.classId -> {
                    setFragment(clazz)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bookId -> {
                    setFragment(book)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.facultyId -> {
                    setFragment(faculty)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.schoolId -> {
                    setFragment(school)
                    return@setOnNavigationItemSelectedListener true
                }


                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
        navigation.setNavigationItemSelectedListener { item ->

            when (item.itemId) {
                R.id.homes -> {
                    setTemp(navHome)
                    supportFragmentManager.beginTransaction().replace(R.id.container, navHome())
                        .commit()
                    navigation.setCheckedItem(R.id.homes)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }

                R.id.homeId -> {
                    setFragment(home)
                    supportFragmentManager.beginTransaction().replace(R.id.container, Home())
                        .commit()
                    navigation.setCheckedItem(R.id.homeId)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }


                R.id.adds -> {
                    setTemp(addSv)
                    supportFragmentManager.beginTransaction().replace(R.id.container, AddSv())
                        .commit()
                    navigation.setCheckedItem(R.id.adds)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }

                R.id.detail -> {
                    setTemp(details)
                    supportFragmentManager.beginTransaction().replace(R.id.container, Details())
                        .commit()
                    navigation.setCheckedItem(R.id.detail)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }

                R.id.studentMn -> {
                    setTemp(studentMn)
                    supportFragmentManager.beginTransaction().replace(R.id.container, StudentMn())
                        .commit()
                    navigation.setCheckedItem(R.id.studentMn)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }

                R.id.language -> {
                    setTemp(language)
                    supportFragmentManager.beginTransaction().replace(R.id.container, Language())
                        .commit()
                    navigation.setCheckedItem(R.id.language)
                    drawerLayout.closeDrawer(GravityCompat.START)
                    return@setNavigationItemSelectedListener true
                }

                R.id.logout -> {
                    logOut()
                    return@setNavigationItemSelectedListener true
                }

                else -> {
                    return@setNavigationItemSelectedListener false
                }

            }
        }


    }

    fun logOut() {
        startActivity(Intent(this@UserActivity, MainActivity::class.java))
    }

    fun setFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    fun setTemp(temp: Fragment) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, temp)
        fragmentTransaction.commit()
    }


}