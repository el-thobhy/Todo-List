package com.elthobhy.todolist.views.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.elthobhy.todolist.R
import com.elthobhy.todolist.databinding.ActivityMainBinding
import com.elthobhy.todolist.views.home.HomeFragment
import com.elthobhy.todolist.views.newtask.NewTaskActivity
import com.elthobhy.todolist.views.taskcomplete.TaskCompleteFragment

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding  get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupBottomNavigation()
        onClick()
    }

    private fun onClick() {
        binding?.btnAddTask?.setOnClickListener {
            startActivity(Intent(this, NewTaskActivity::class.java))
        }
    }

    private fun setupBottomNavigation() {
        binding?.btmNavMain?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.action_home ->{
                    openHomeFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.action_task_complete->{
                    openHomeFragment(TaskCompleteFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
        binding?.btmNavMain?.selectedItemId = R.id.action_home
    }

    private fun openHomeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frameMain, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}