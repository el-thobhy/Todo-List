package com.elthobhy.todolist.views.newtask

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elthobhy.todolist.R
import com.elthobhy.todolist.adapter.AddSubTaskAdapter
import com.elthobhy.todolist.databinding.ActivityNewTaskBinding
import com.elthobhy.todolist.model.SubTask
import com.elthobhy.todolist.utils.DatePickerSet

class NewTaskActivity : AppCompatActivity() {

    private var _binding : ActivityNewTaskBinding? = null
    private val binding get() = _binding
    private lateinit var addSubTaskAdapter: AddSubTaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setupActionBar()
        setupAddSubTaskAdapter()
        onClick()
    }

    private fun onClick() {
        binding?.tbNewTask?.setNavigationOnClickListener {
            finish()
        }
        binding?.btnAddDateTask?.setOnClickListener {
            DatePickerSet.showDatePicker(this,
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                val dateString = DatePickerSet.dateFormatSql(year, month, day)
                binding?.btnAddDateTask?.text = DatePickerSet.dateFromSqlToDateViewTask(dateString)

                checkDateFilled(true)
            })
        }
        binding?.btnRemoveDateTask?.setOnClickListener {
            binding?.btnAddDateTask?.text = null
            checkDateFilled(false)
        }

        binding?.btnAddSubTask?.setOnClickListener {
            val subTask = SubTask(null,null,"")
            addSubTaskAdapter.addTask(subTask)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_task_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_remove_task -> {
                Toast.makeText(applicationContext,"Remove Task", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkDateFilled(b: Boolean) {
        if(b){
            binding?.btnAddDateTask?.background = ContextCompat.getDrawable(this, R.drawable.bg_btn_add_date_task)
            binding?.btnAddDateTask?.setPadding(24,24,24,24)
            binding?.btnRemoveDateTask?.visibility = View.VISIBLE
        }else{
            binding?.btnAddDateTask?.setBackgroundResource(0)
            binding?.btnAddDateTask?.setPadding(0,0,0,0)
            binding?.btnRemoveDateTask?.visibility = View.GONE
        }
    }

    private fun setupAddSubTaskAdapter() {
        addSubTaskAdapter = AddSubTaskAdapter()
        binding?.rvAddSubTask?.adapter = addSubTaskAdapter
    }



    private fun setupActionBar() {
        setSupportActionBar(binding?.tbNewTask)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}