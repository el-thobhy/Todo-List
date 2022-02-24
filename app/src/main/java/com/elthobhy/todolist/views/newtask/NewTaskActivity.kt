package com.elthobhy.todolist.views.newtask

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elthobhy.todolist.R
import com.elthobhy.todolist.adapter.AddSubTaskAdapter
import com.elthobhy.todolist.databinding.ActivityNewTaskBinding
import com.elthobhy.todolist.db.DbSubTaskHelper
import com.elthobhy.todolist.db.DbTaskHelper
import com.elthobhy.todolist.model.MainTask
import com.elthobhy.todolist.model.SubTask
import com.elthobhy.todolist.model.Task
import com.elthobhy.todolist.utils.DatePickerSet

class NewTaskActivity : AppCompatActivity() {

    private var _binding : ActivityNewTaskBinding? = null
    private val binding get() = _binding
    private lateinit var addSubTaskAdapter: AddSubTaskAdapter
    private lateinit var dbTaskHelper: DbTaskHelper
    private lateinit var dbSubTaskHelper: DbSubTaskHelper
    private var isEdit = false
    private var task: Task? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setup()
        setupActionBar()
        setupAddSubTaskAdapter()
        onClick()
    }

    private fun setup() {
        dbTaskHelper = DbTaskHelper.getInstance(this)
        dbSubTaskHelper = DbSubTaskHelper.getInstance(this)
        addSubTaskAdapter = AddSubTaskAdapter(dbSubTaskHelper)

        getDataExtra()
    }

    private fun getDataExtra() {
        if(intent != null){
            task = intent.getParcelableExtra(EXTRA_TASK)
        }
        if(task != null){
            isEdit = true
            binding?.btnSubmitTask?.text = getString(R.string.update)
            setupView(task)
        }else{
            task = Task(mainTask = MainTask())
        }
    }

    private fun setupView(task: Task?) {
        binding?.etTitleTask?.setText(task?.mainTask?.title)
        binding?.etAddDetailsTask?.setText(task?.mainTask?.details)
        val dateString = task?.mainTask?.date

        if(dateString != null){
            binding?.btnAddDateTask?.text = DatePickerSet.dateFromSqlToDateViewTask(dateString)
            checkDateFilled(true)
        }
    }

    private fun onClick() {
        binding?.tbNewTask?.setNavigationOnClickListener {
            finish()
        }
        binding?.btnAddDateTask?.setOnClickListener {
            DatePickerSet.showDatePicker(this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val dateString = DatePickerSet.dateFormatSql(year, month, dayOfMonth)
                binding?.btnAddDateTask?.text = DatePickerSet.dateFromSqlToDateViewTask(dateString)

                task?.mainTask?.date = dateString
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

        binding?.btnSubmitTask?.setOnClickListener {
            submitDataToDatabase()
        }
    }

    private fun submitDataToDatabase() {
        val titleTask = binding?.etTitleTask?.text.toString()
        val detailTask = binding?.etAddDetailsTask?.text.toString()
        val dataSubTask = addSubTaskAdapter.getData()

        if(titleTask.isEmpty()){
            binding?.etTitleTask?.error = getString(R.string.please_field_your_title)
            binding?.etTitleTask?.requestFocus()
            return
        }

        task?.mainTask?.title = titleTask

        if(detailTask.isNotEmpty()){
            task?.mainTask?.details = detailTask
        }

        if(isEdit){
            //ToDO EDIT
            val result = dbTaskHelper.updateTask(task?.mainTask)
            if(result>0){
                if(dataSubTask != null && dataSubTask.isNotEmpty()){
                    var isSuccess = false
                    for(subTask: SubTask in dataSubTask){
                        if(subTask.id != null){
                            val resultSubTask = dbSubTaskHelper.updateSubTask(subTask)
                            isSuccess = resultSubTask>0
                        }else{
                            subTask.idTask = task?.mainTask?.id
                            val resultSubTask = dbSubTaskHelper.insert(subTask)
                            isSuccess = resultSubTask>0
                        }
                    }
                    if(isSuccess){
                        val dialog = showSuccessDialog(getString(R.string.success_dialog))
                        Handler().postDelayed({
                              dialog.dismiss()
                        }, 1200)
                    }else{
                        val dialog = showFailedDialog(getString(R.string.failed_dialog))
                        Handler().postDelayed({
                              dialog.dismiss()
                        },1200)
                    }
                }
                val dialog = showSuccessDialog(getString(R.string.success_dialog))
                Handler().postDelayed({
                    dialog.dismiss()
                },1200)
            }else{
                val dialog = showFailedDialog(getString(R.string.failed_dialog))
                Handler().postDelayed({
                    dialog.dismiss()
                },1200)
            }
        }else{
            val result = dbTaskHelper.insert(task?.mainTask)
            if(result>0){
                if(dataSubTask !=null && dataSubTask.isNotEmpty()){
                    var isSuccess = false
                    for(subtask: SubTask in dataSubTask){
                        subtask.idTask = result.toInt()
                        val resultSubTask = dbSubTaskHelper.insert(subtask)
                        isSuccess = resultSubTask>0
                    }
                    if(isSuccess){
                        val dialog = showSuccessDialog(getString(R.string.success_dialog))
                        Handler().postDelayed({
                              dialog.dismiss()
                        }, 1200)
                    }else{
                        val dialog = showFailedDialog(getString(R.string.failed_dialog))
                        Handler().postDelayed({
                            dialog.dismiss()
                        }, 1200)
                    }
                }
                val dialog = showSuccessDialog(getString(R.string.success_dialog))
                Handler().postDelayed({
                    dialog.dismiss()
                    finish()
                }, 1200)
            }else{
                val dialog = showFailedDialog("Failed add data to Database")
                Handler().postDelayed({
                    dialog.dismiss()
                }, 1200)
            }
        }
    }

    private fun showFailedDialog(s: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Failed")
            .setMessage(s)
            .show()
    }

    private fun showSuccessDialog(s: String): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Success")
            .setMessage(s)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(isEdit){
            menuInflater.inflate(R.menu.new_task_menu, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_remove_task -> {
                Toast.makeText(applicationContext,"Remove Task", Toast.LENGTH_SHORT).show()
                AlertDialog.Builder(this)
                    .setTitle("Delete")
                    .setMessage("Apakah anda yakin ingin menghapus?")
                    .setPositiveButton("Yes"){dialog,_->
                        task?.mainTask?.id?.let {
                            val result = dbTaskHelper.deleteTask(it)
                            if(result >0){
                                val dialogSuccer = showSuccessDialog("Dihapus")
                                Handler().postDelayed({
                                      dialogSuccer.dismiss()
                                    dialog.dismiss()
                                    finish()
                                },1200)
                            }
                        }
                    }
                    .setNegativeButton("No"){dialog,_->
                        dialog.dismiss()
                    }
                    .show()
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
        task?.subTask?.let { addSubTaskAdapter.setData(it) }
        binding?.rvAddSubTask?.adapter = addSubTaskAdapter
    }



    private fun setupActionBar() {
        setSupportActionBar(binding?.tbNewTask)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }

    companion object{
        const val EXTRA_TASK = "extra_task"
    }
}