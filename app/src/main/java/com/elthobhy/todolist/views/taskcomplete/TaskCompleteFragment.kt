package com.elthobhy.todolist.views.taskcomplete

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elthobhy.todolist.R
import com.elthobhy.todolist.adapter.TaskAdapter
import com.elthobhy.todolist.databinding.FragmentTaskCompleteBinding
import com.elthobhy.todolist.db.DbSubTaskHelper
import com.elthobhy.todolist.db.DbTaskHelper
import com.elthobhy.todolist.model.SubTask
import com.elthobhy.todolist.model.Task
import com.elthobhy.todolist.repository.TaskRepository

class TaskCompleteFragment : Fragment() {

    private var _binding : FragmentTaskCompleteBinding? = null
    private val binding get() = _binding
    private lateinit var dbTaskHelper: DbTaskHelper
    private lateinit var dbSubTaskHelper: DbSubTaskHelper
    private lateinit var taskAdapter: TaskAdapter
    private var tasks: List<Task>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTaskCompleteBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        onClick()
    }

    private fun onClick() {
        binding?.fabDeleteTaskComplete?.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete All Data")
                .setMessage("Apakah anda yakin ingin menghapus semua data?")
                .setPositiveButton("Yes"){dialog,_->
                    if(tasks!=null){
                        val result = dbTaskHelper.deleteAllTaskComplete()
                        if(result>0){
                            val dialogDeleteSuccess = showSuccessDeleteAllComplete()
                            Handler().postDelayed({
                                dialog.dismiss()
                                dialogDeleteSuccess.dismiss()
                                taskAdapter.deleteAllDataTask()
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

    private fun showSuccessDeleteAllComplete(): AlertDialog {
        return AlertDialog.Builder(context)
            .setTitle("Success")
            .setMessage("Berhasil menghapus semua data")
            .show()
    }

    private fun setup() {
        dbTaskHelper = DbTaskHelper.getInstance(context)
        dbSubTaskHelper = DbSubTaskHelper.getInstance(context)
        taskAdapter = TaskAdapter(dbTaskHelper, dbSubTaskHelper)
    }

    override fun onResume() {
        super.onResume()
        getDataTask()
    }

    private fun getDataTask() {
        tasks = TaskRepository.getDataTaskCompleteFromDatabase(dbTaskHelper, dbSubTaskHelper)

        if(tasks!= null && tasks!!.isNotEmpty()){
            showCompleteTask()
            taskAdapter.setData(tasks!!)

            binding?.rvTaskComplete?.adapter = taskAdapter
        }else{
            hideTaskComplete()
        }
    }

    private fun hideTaskComplete() {
        binding?.rvTaskComplete?.visibility = View.GONE
        binding?.layoutEmptyTaskComplete?.root?.visibility = View.VISIBLE
        binding?.fabDeleteTaskComplete?.visibility = View.GONE
    }

    private fun showCompleteTask() {
        binding?.rvTaskComplete?.visibility = View.VISIBLE
        binding?.layoutEmptyTaskComplete?.root?.visibility = View.GONE
        binding?.fabDeleteTaskComplete?.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}